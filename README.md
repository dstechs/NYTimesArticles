# Basic App Structure and Articles Fetched from New York Times and displayed
Code is only to show specific articles based on the section predifined by api from NY Times.

## Requirements

Appliction supports Android Lollipop and later.

## Flow of Application

Very simple flow in application, just open the app you will be directed to dashboard where all the list of Most Popular Articles from New York Times is listed. Listing are best of 7 days.
It has facility to get new as well as load more when swiping or scrolling list. Once you click on any article the details of specific is displayed. Thats it.

## Find Screenshots attached
[Click Here](screenshots)

## How to use the Code for better result
As per the API requriement I have made global type for the api type and session type
```java
    enum class REQUEST_TYPE {// this is needed when we are to use other api related to article, just mention the api type

        MOST_POPULAR {
            override fun getRequestType(): String {
                return "mostpopular/v2/mostviewed"
            }
        };
        
    //  example for new one --> BOOKS{
    //    override fun getRequestType(): String {
    //            return "books/latest"
    //        }
    //    };

        abstract fun getRequestType(): String
    }

    enum class SECTIONS {// this is needed to define all the sections and use as per need
        ALL_SECTION {
            override fun getSection(): String {
                return "all-sections"
            }
        };
        
        //  example for new one --> CATEGORY{
    //    override fun getRequestType(): String {
    //            return "books"
    //        }
    //    };

        abstract fun getSection(): String
    }
```

## Make a Request call for data from API
```java
        RetroCalls.get()
                .getDataFromServer(ApiConstant.REQUEST_TYPE.MOST_POPULAR, ApiConstant.SECTIONS.ALL_SECTION, "7", (offset * 20).toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(object : DisposableObserver<ResponseParser<MutableList<ResultModel>>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseParser<MutableList<ResultModel>>) {
                        if (t.getStatus()) {
                            //data available
                        } else {
                           //api didnot sent any data
                        }
                    }

                    override fun onError(e: Throwable) {
                    }
                })
```
## Method that handles for the api request generator with binding data to url as per NY Times
```java
mRequestType - "mostpopular/v2/mostviewed"
mSection - "all-sections"
offset- as per API rule 
mDays - 1/7/30
getDataFromServer(mRequestType: ApiConstant.REQUEST_TYPE, mSection: ApiConstant.SECTIONS, mDays: String, offset: String): Observable<ResponseParser<MutableList<ResultModel>>>
```

### Some Global Methods when Using BaseActivity or BaseActivityDelegation
```java
    class TestActivty: BaseActivity(){
        /**
         * this method is called when before activity is destroyed
         * can be use for free memory
         * */
        override fun freeResource() {
            
        }
    
        /**
         * first method to be called by super class as view is provided it
         */
        override fun setViewLayout(): Int {
            return R.layout.activity_article_details
        }
        
        //some additional methods for better display series with some managed callback 
    
        /**
         * This is called once the layout is visible means when onCreate() finishes its work
         * Here we can do our basic initializatino or start animation etc
         * calls for one time
         */
        override fun onLayoutAdded(savedInstanceState: Bundle?) {
        }
    
        /**
         * This is the called when all of the activity operation is finised like
         * Loading, Displaying, and setup
         * Now we can display the data without any hesitation, as all android work is finised
         * Simply show or start animation as android gives all the handle of app to you
         * calls for one time 
         */
        override fun canPlotData() {
            
        }
    
        /**
         * As name suggest its used for receiving any network change status
         * can handled flow when network changes
         */
        override fun onNetworkChange(isConnected: Boolean) {
            
        }

    }
```

## BaseDelegation Activity Pattern for Use
```java
    class TestDelegationActivty : BaseActivityDelation<MainActivityPresentor, MainActivityView>(), MainActivityView {
        /**
         * Method called when data is available from Service
         */
    
        override fun onDataAvailable() {
    
        }
    
        /**
         * Method is called when no data is available from service
         *
         */
        override fun onDataNotFound() {
        }
    
        /**
         * View handling, use this method to show start progress of any task
         */
    
        override fun showProgress() {
        }
    
        /**
         * View handling, use this method to manage any running progress
         */
    
        override fun hideProgress() {
        }
    
        /**
         * called from presentor if needed
         */
    
        override fun destroy() {
        }
    
        /**
         * Method used to initialize Presentor can be null
         */
    
        override fun setPresentor(): MainActivityPresentor? {
            return null
        }
    
        /**
         * Method is for initialize View callback
         */
    
        override fun onAttach(): MainActivityView {
            return this
        }
    
        /**
         * Method is called when before activity is destroyed
         * can be use for free memory
         * */
        override fun freeResource() {
    
        }
    
        /**
         * Method called by super class for view to show in activity
         */
        override fun setViewLayout(): Int {
            return R.layout.activity_article_details
        }
    
        /**
         * This is called once the layout is visible means when onCreate() finishes its work
         * Here we can do our basic initializatino or start animation etc
         * calls for one time
         */
        override fun onLayoutAdded(savedInstanceState: Bundle?) {
        }
    
        /**
         * This is the called when all of the activity operation is finised like
         * Loading, Displaying, and setup
         * Now we can display the data without any hesitation, as all android work is finised
         * Simply show or start animation as android gives all the handle of app to you
         * calls for one time
         */
        override fun canPlotData() {
    
        }
    
        /**
         * As name suggest its used for receiving any network change status
         * can handled flow when network changes
         */
        override fun onNetworkChange(isConnected: Boolean) {
    
        }

    }

```

## As Of TestCase I have created test for JUnit Api test and Instrumental for just the flow
```java
    // Junit testing using mokito
    @Test
    fun successRequestTest() {
        Mockito.`when`(view?.isTesting()).thenReturn(true)
        presentor?.requestData(requestType = ApiConstant.REQUEST_TYPE.MOST_POPULAR)
        Mockito.verify(view)?.onDataAvailable()
    }

    @Test
    fun failureRequestTest() {
        Mockito.`when`(view!!.isTesting()).thenReturn(true)
        presentor?.requestData(requestType = ApiConstant.REQUEST_TYPE.FAKE)
        Mockito.verify(view)?.onDataNotFound()
    }

```

## Testing using AndroidJUnit
```java
     @Test
    fun launch_activity() {
        Thread.sleep(8000)
        Espresso.onView(ViewMatchers.withId(R.id.rvContent)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Thread.sleep(5000)

    }

```
