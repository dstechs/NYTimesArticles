package com.dinesh.kotlinstructure.router

object ApiConstant {

    const val KEY = "734e6cdb72a5415b8c5f2b7bdf0359ae"
    val API_KEY: String = "api-key"
    val API_OFFSET: String = "offset"

    object URL {
        const val BASE = "http://api.nytimes.com/svc/"
    }

    enum class REQUEST_TYPE {

        MOST_POPULAR {
            override fun getRequestType(): String {
                return "mostpopular/v2/mostviewed"
            }
        },
        FAKE {
            override fun getRequestType(): String {
                return "mostviewed"
            }
        };

        abstract fun getRequestType(): String
    }

    enum class SECTIONS {
        ALL_SECTION {
            override fun getSection(): String {
                return "all-sections"
            }
        };

        abstract fun getSection(): String
    }
}
