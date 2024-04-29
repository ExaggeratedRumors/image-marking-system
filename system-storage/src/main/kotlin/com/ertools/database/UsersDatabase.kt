package com.ertools.database


import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

private val client = KMongo.createClient().coroutine
private val database = client.getDatabase("UsersDatabase")

private val users = database.getCollection<User>()

suspend fun getUserById(id: String): User? {
    return users.findOneById(id)
}

suspend fun createEmployeeOrUpdateEmployeeForId(user: User): Boolean {
    val employeeExists = users.findOneById(user.id) != null
    return if (employeeExists) {
        users.updateOneById(user.id, user).wasAcknowledged()
    } else {
        user.id = ObjectId().toString()
        users.insertOne(user).wasAcknowledged()
    }
}

suspend fun deleteEmployeeById(userId: String): Boolean {
    val user = users.findOne(User::id eq userId)
    user?.let { _ ->
        return users.deleteOneById(user.id).wasAcknowledged()
    } ?: return false

}