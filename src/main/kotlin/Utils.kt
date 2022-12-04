fun <T, U> List<Pair<T?, U?>>.filterPairs(): List<Pair<T, U>> =
    mapNotNull { (t, u) ->
        if (t == null || u == null) null else t to u
    }