package jeuDeLaVie


class FlexibleRuleStandardRule : FlexibleRules("flexibleStandardRule",
        BooleanArray(27){i->when(i){
            2 -> false
            3 -> false
            else -> true
    }},
        BooleanArray(27){i->when(i){
            3 -> true
            else -> false
        }})