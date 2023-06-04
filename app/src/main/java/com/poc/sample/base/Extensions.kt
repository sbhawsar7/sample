package com.poc.base

val Any.displayName: String
    get() = "${javaClass.simpleName}[${hashCode().toString(16)}]"

