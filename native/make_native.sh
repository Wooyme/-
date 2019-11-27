#!/usr/bin/env bash
SL_BUILD_NATIVE="false"
if [[ $SL_BUILD_NATIVE == "false" ]]; then
    echo "Skipping the native image build because SL_BUILD_NATIVE is set to false."
    exit 0
fi
"$JAVA_HOME"/bin/native-image \
    -H:EnableURLProtocols=http \
    --macro:truffle --no-fallback --initialize-at-build-time \
    -cp ../language/target/simplelanguage.jar:../launcher/target/launcher-19.2.0-SNAPSHOT.jar \
    SLMain \
    slnative
