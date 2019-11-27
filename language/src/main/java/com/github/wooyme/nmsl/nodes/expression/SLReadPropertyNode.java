/*
 * Copyright (c) 2013, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The Universal Permissive License (UPL), Version 1.0
 *
 * Subject to the condition set forth below, permission is hereby granted to any
 * person obtaining a copy of this software, associated documentation and/or
 * data (collectively the "Software"), free of charge and under any and all
 * copyright rights in the Software, and any and all patent rights owned or
 * freely licensable by each licensor hereunder covering either (i) the
 * unmodified Software as contributed to or provided by such licensor, or (ii)
 * the Larger Works (as defined below), to deal in both
 *
 * (a) the Software, and
 *
 * (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
 * one is included with the Software each a "Larger Work" to which the Software
 * is contributed by such licensors),
 *
 * without restriction, including without limitation the rights to copy, create
 * derivative works of, display, perform, and distribute the Software and make,
 * use, sell, offer for sale, import, export, have made, and have sold the
 * Software and the Larger Work(s), and to sublicense the foregoing rights on
 * either these or other terms.
 *
 * This license is subject to the following condition:
 *
 * The above copyright notice and either this complete permission notice or at a
 * minimum a reference to the UPL must be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.wooyme.nmsl.nodes.expression;

import com.github.wooyme.nmsl.SLException;
import com.github.wooyme.nmsl.nodes.SLExpressionNode;
import com.github.wooyme.nmsl.nodes.util.SLToMemberNode;
import com.github.wooyme.nmsl.runtime.SLFunction;
import com.github.wooyme.nmsl.runtime.SLNull;
import com.github.wooyme.nmsl.runtime.SLReflection;
import com.github.wooyme.nmsl.runtime.SLUndefinedNameException;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.*;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.List;

/**
 * The node for reading a property of an object. When executed, this node:
 * <ol>
 * <li>evaluates the object expression on the left hand side of the object access operator</li>
 * <li>evaluated the property name</li>
 * <li>reads the named property</li>
 * </ol>
 */
@NodeInfo(shortName = ".")
@NodeChild("receiverNode")
@NodeChild("nameNode")
public abstract class SLReadPropertyNode extends SLExpressionNode {

    static final int LIBRARY_LIMIT = 3;

    @Specialization(limit = "LIBRARY_LIMIT")
    protected Object readArray(List receiver, Object index,
                               @CachedLibrary("index") InteropLibrary numbers) {
        try {
            Object obj = receiver.get(numbers.asInt(index));
            if(obj instanceof SLFunction){
                ((SLFunction) obj).setSelf(receiver);
            }
            return obj;
        } catch (UnsupportedMessageException | IndexOutOfBoundsException e ) {
            // read was not successful. In SL we only have basic support for errors.
            throw SLUndefinedNameException.undefinedProperty(this, index);
        }
    }

    @Specialization(limit = "LIBRARY_LIMIT")
    protected Object readString(String receiver, Object index,
                               @CachedLibrary("index") InteropLibrary numbers) {
        try {
            return new String(new char[]{receiver.toCharArray()[numbers.asInt(index)]});
        } catch (UnsupportedMessageException | IndexOutOfBoundsException e ) {
            // read was not successful. In SL we only have basic support for errors.
            throw SLUndefinedNameException.undefinedProperty(this, index);
        }
    }

    @Specialization(limit = "LIBRARY_LIMIT")
    protected Object readObject(VirtualFrame frame, SLReflection obj, Object name, @Cached SLToMemberNode asMember){
        try {
            return obj.readProperty(asMember.execute(name),frame);
        } catch (UnknownIdentifierException | UnsupportedMessageException | ArityException | UnsupportedTypeException | NoSuchMethodException e) {
            throw SLUndefinedNameException.undefinedProperty(this,e.getMessage());
        }
    }

    @Specialization(limit = "LIBRARY_LIMIT")
    protected Object readStatic(Class clazz,Object name,@Cached SLToMemberNode asMember){
        try{
            try {
                return clazz.getDeclaredField(asMember.execute(name)).get(null);
            } catch (IllegalAccessException|NoSuchFieldException e) {
                return new SLReflection.SLReflectionMethod(clazz,asMember.execute(name));
            }
        }catch (UnknownIdentifierException | NoSuchMethodException ex){
            throw SLUndefinedNameException.undefinedProperty(this, name);
        }
    }

    @Specialization(guards = "arrays.hasArrayElements(receiver)", limit = "LIBRARY_LIMIT")
    protected Object readArray(TruffleObject receiver, Object index,
                               @CachedLibrary("receiver") InteropLibrary arrays,
                               @CachedLibrary("index") InteropLibrary numbers) {
        try {
            Object obj = arrays.readArrayElement(receiver, numbers.asLong(index));
            if(obj instanceof SLFunction){
                ((SLFunction) obj).setSelf(receiver);
            }
            return obj;
        } catch (UnsupportedMessageException | InvalidArrayIndexException e) {
            // read was not successful. In SL we only have basic support for errors.
            throw SLUndefinedNameException.undefinedProperty(this, index);
        }
    }

    @Specialization(guards = "objects.hasMembers(receiver)", limit = "LIBRARY_LIMIT")
    protected Object readObject(TruffleObject receiver, Object name,
                    @CachedLibrary("receiver") InteropLibrary objects,
                    @Cached SLToMemberNode asMember) {
        try {
            Object obj = objects.readMember(receiver, asMember.execute(name));
            if(obj instanceof SLFunction){
                ((SLFunction) obj).setSelf(receiver);
            }
            return obj;
        } catch (UnsupportedMessageException | UnknownIdentifierException e) {
            // read was not successful. In SL we only have basic support for errors.
            throw SLUndefinedNameException.undefinedProperty(this, name);
        }
    }


    @Fallback
    @CompilerDirectives.TruffleBoundary
    public Object typeError(Object receiver,Object name){
        if(receiver instanceof SLNull){
            throw new SLException("Receiver cannot be null",this);
        }else{
            throw new SLException("Unsupported receiver "+receiver+" when reading property "+name,this);
        }
    }
}
