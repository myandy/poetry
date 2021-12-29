package com.myth.shishi.ai

import java.lang.Exception

class UnmappedWordException(val word:String): Exception("unmapped word($word) to index") {
}