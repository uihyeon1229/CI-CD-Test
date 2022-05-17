#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/

if [ -d $REPOSITORY/action ]; then
    rm -rf $REPOSITORY/action
fi
mkdir -vp $REPOSITORY/action