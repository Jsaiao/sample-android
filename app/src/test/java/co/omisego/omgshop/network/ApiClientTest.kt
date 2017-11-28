package co.omisego.omgshop.network

import co.omisego.androidsdk.models.Response
import co.omisego.omgshop.testutils.RecordingObserver
import co.omisego.omgshop.models.Login
import co.omisego.omgshop.models.Product
import co.omisego.omgshop.models.Register
import co.omisego.omgshop.models.User
import co.omisego.omgshop.testutils.readFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.mock
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger


/**
 * OmiseGO
 *
 *
 * Created by Phuchit Sirimongkolsathien on 11/27/2017 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

@Suppress("IllegalIdentifier")
class ApiClientTest {
    private lateinit var omiseGOAPI: OmiseGOAPI

    private val observerRule = RecordingObserver.Rule()
    private val mockWebServer: MockWebServer = MockWebServer()

    private lateinit var userFile: File
    private lateinit var registerFile: File
    private lateinit var loginFile: File
    private lateinit var productGetFile: File
    private lateinit var productBuyFile: File
    private lateinit var responseUser: Response<User.Response>
    private lateinit var responseRegister: Response<Register.Response>
    private lateinit var responseLogin: Response<Login.Response>
    private lateinit var responseProductBuy: Response<Nothing>
    private lateinit var responseProductGet: Response<Product.Get.Response>


    @Before
    fun setUp() {
        // Retrieves mocked /me.get response from local json file
        userFile = readFile("user.response.success.json")
        userFile shouldNotBe null

        // Retrieves mocked /signup response from local json file
        registerFile = readFile("signup.response.success.json")
        registerFile shouldNotBe null

        // Retrieves mocked /login response from local json file
        loginFile = readFile("login.response.success.json")
        loginFile shouldNotBe null

        // Retrieves mocked /products.get response from local json file
        productGetFile = readFile("products.get.response.success.json")
        productGetFile shouldNotBe null

        // Retrieves mocked /products.buy response from local json file
        productBuyFile = readFile("products.buy.response.success.json")
        productBuyFile shouldNotBe null

        // Convert response using Gson converter
        val typeUserToken = object : TypeToken<Response<User.Response>>() {}.type
        responseUser = Gson().fromJson<Response<User.Response>>(userFile.readText(), typeUserToken)

        val typeRegisterToken = object : TypeToken<Response<Register.Response>>() {}.type
        responseRegister = Gson().fromJson<Response<Register.Response>>(registerFile.readText(), typeRegisterToken)

        val typeLoginToken = object : TypeToken<Response<Login.Response>>() {}.type
        responseLogin = Gson().fromJson<Response<Login.Response>>(loginFile.readText(), typeLoginToken)

        val typeProductGetToken = object : TypeToken<Response<Product.Get.Response>>() {}.type
        responseProductGet = Gson().fromJson<Response<Product.Get.Response>>(productGetFile.readText(), typeProductGetToken)

        val typeProductBuyToken = object : TypeToken<Response<Nothing>>() {}.type
        responseProductBuy = Gson().fromJson<Response<Nothing>>(productBuyFile.readText(), typeProductBuyToken)

        // Setup retrofit with mock server
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mockWebServer.url("/"))
                .build()

        ApiClient.omiseGO = retrofit.create(OmiseGOAPI::class.java)

        omiseGOAPI = ApiClient.omiseGO

        // Disable unused logged from mock webserver
        LogManager.getLogManager().reset()
        Logger.getGlobal().level = Level.OFF
    }

    @Test
    fun `verify get user api return observable user correctly`() {
        // Enqueued response from mock server
        mockWebServer.enqueue(MockResponse().setBody(userFile.readText()))

        // create mock observable
        val observer = observerRule.create<Response<User.Response>>()

        // subscribe mocked response to observable
        omiseGOAPI.getUser().subscribe(observer)

        // assert observable value
        observer.assertValue(responseUser).assertComplete()
    }

    @Test
    fun `verify sign up api return observable register response correctly`() {
        // Enqueued response from mock server
        mockWebServer.enqueue(MockResponse().setBody(registerFile.readText()))

        // create mock observable
        val observer = observerRule.create<Response<Register.Response>>()

        // subscribe mocked response to observable
        omiseGOAPI.signup(mock()).subscribe(observer)

        // assert observable value
        observer.assertValue(responseRegister).assertComplete()
    }

    @Test
    fun `verify login api return observable register response correctly`() {
        // Enqueued response from mock server
        mockWebServer.enqueue(MockResponse().setBody(loginFile.readText()))

        // create mock observable
        val observer = observerRule.create<Response<Login.Response>>()

        // subscribe mocked response to observable
        omiseGOAPI.login(mock()).subscribe(observer)

        // assert observable value
        observer.assertValue(responseLogin).assertComplete()
    }

    @Test
    fun `verify product buy api return observable register response correctly`() {
        // Enqueued response from mock server
        mockWebServer.enqueue(MockResponse().setBody(productBuyFile.readText()))

        // create mock observable
        val observer = observerRule.create<Response<Nothing>>()

        // subscribe mocked response to observable
        omiseGOAPI.buy(mock()).subscribe(observer)

        // assert observable value
        observer.assertValue(responseProductBuy).assertComplete()
    }

    @Test
    fun `verify product get api return observable register response correctly`() {
        // Enqueued response from mock server
        mockWebServer.enqueue(MockResponse().setBody(productGetFile.readText()))

        // create mock observable
        val observer = observerRule.create<Response<Product.Get.Response>>()

        // subscribe mocked response to observable
        omiseGOAPI.getProducts().subscribe(observer)

        // assert observable value
        observer.assertValue(responseProductGet).assertComplete()
    }
}