// Generated from /home/wooyme/Projects/simplelanguage/language/src/main/java/com/github/wooyme/nmsl/parser/SimpleLanguage.g4 by ANTLR 4.7.2
package com.github.wooyme.nmsl.parser;

// DO NOT MODIFY - generated from SimpleLanguage.g4 using "mx create-sl-parser"

import java.util.*;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.RootCallTarget;
import com.github.wooyme.nmsl.SLLanguage;
import com.github.wooyme.nmsl.nodes.SLExpressionNode;
import com.github.wooyme.nmsl.nodes.SLStatementNode;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpleLanguageParser}.
 */
public interface SimpleLanguageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#simplelanguage}.
	 * @param ctx the parse tree
	 */
	void enterSimplelanguage(SimpleLanguageParser.SimplelanguageContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#simplelanguage}.
	 * @param ctx the parse tree
	 */
	void exitSimplelanguage(SimpleLanguageParser.SimplelanguageContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(SimpleLanguageParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(SimpleLanguageParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#lambda}.
	 * @param ctx the parse tree
	 */
	void enterLambda(SimpleLanguageParser.LambdaContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#lambda}.
	 * @param ctx the parse tree
	 */
	void exitLambda(SimpleLanguageParser.LambdaContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#label_block}.
	 * @param ctx the parse tree
	 */
	void enterLabel_block(SimpleLanguageParser.Label_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#label_block}.
	 * @param ctx the parse tree
	 */
	void exitLabel_block(SimpleLanguageParser.Label_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SimpleLanguageParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SimpleLanguageParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(SimpleLanguageParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(SimpleLanguageParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile_statement(SimpleLanguageParser.While_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile_statement(SimpleLanguageParser.While_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_statement(SimpleLanguageParser.If_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_statement(SimpleLanguageParser.If_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_statement(SimpleLanguageParser.Return_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_statement(SimpleLanguageParser.Return_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SimpleLanguageParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SimpleLanguageParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#middle_term}.
	 * @param ctx the parse tree
	 */
	void enterMiddle_term(SimpleLanguageParser.Middle_termContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#middle_term}.
	 * @param ctx the parse tree
	 */
	void exitMiddle_term(SimpleLanguageParser.Middle_termContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#logic_term}.
	 * @param ctx the parse tree
	 */
	void enterLogic_term(SimpleLanguageParser.Logic_termContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#logic_term}.
	 * @param ctx the parse tree
	 */
	void exitLogic_term(SimpleLanguageParser.Logic_termContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#logic_factor}.
	 * @param ctx the parse tree
	 */
	void enterLogic_factor(SimpleLanguageParser.Logic_factorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#logic_factor}.
	 * @param ctx the parse tree
	 */
	void exitLogic_factor(SimpleLanguageParser.Logic_factorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic(SimpleLanguageParser.ArithmeticContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic(SimpleLanguageParser.ArithmeticContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(SimpleLanguageParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(SimpleLanguageParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#single_factor}.
	 * @param ctx the parse tree
	 */
	void enterSingle_factor(SimpleLanguageParser.Single_factorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#single_factor}.
	 * @param ctx the parse tree
	 */
	void exitSingle_factor(SimpleLanguageParser.Single_factorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(SimpleLanguageParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(SimpleLanguageParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleLanguageParser#member_expression}.
	 * @param ctx the parse tree
	 */
	void enterMember_expression(SimpleLanguageParser.Member_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleLanguageParser#member_expression}.
	 * @param ctx the parse tree
	 */
	void exitMember_expression(SimpleLanguageParser.Member_expressionContext ctx);
}