package com.todo.todo

class GoalDataModel {
    companion object {
        fun addFirstGoal(goal: String): String {
            val map = HashMap<String, Boolean>()
            map.put(goal, false)
            return hashMapToString(map)
        }

        fun retrieveFirstGoal(string: String): String {
            val map = stringToHashMap(string)
            return map.keys.stream().findFirst().get()
        }

        private fun hashMapToString(map: HashMap<String, Boolean>) = map.toString()

        //    "{SALE_PRODUCTS=true, EXPENSES=true, EXPENSES_ITEMS=false, SALES=false}"
        private fun stringToHashMap(string: String): HashMap<String, Boolean> {
            val map = HashMap<String, Boolean>()
            val pairs = string.removePrefix("{").removeSuffix("}").split(", ")

            for (i in 0 until pairs.count()) {
                val pair = pairs[i]
                val keyValue = pair.split("=")
                map.put(keyValue[0], keyValue[1].toBoolean())
            }

            return map
        }
    }
}