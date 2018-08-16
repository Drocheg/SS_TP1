package jeuDeLaVie

class FlexibleRule5766 : FlexibleRules("flexibleRule5677",
        BooleanArray(27){i->when(i){
            5 -> false
            6 -> false
            7 -> false
            else -> true
    }},
        BooleanArray(27){i->when(i){
            6 -> true
            else -> false
        }})