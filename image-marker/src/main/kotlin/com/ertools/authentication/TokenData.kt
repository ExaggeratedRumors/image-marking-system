package com.ertools.authentication

class TokenData (
    var token: String,
    var timestamp: Long
) {
   fun updateData(token: String, timestamp: Long) {
       this.token = token
       this.timestamp = timestamp
   }
}