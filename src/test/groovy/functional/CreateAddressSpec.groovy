package functional

import groovy.json.JsonBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import spock.lang.Specification

class CreateAddressSpec  extends Specification {

    def httpClient = new RESTClient("http://127.0.0.1:8080", ContentType.JSON)



    /*
    ** Get Testing
     */

    def "save an address and the get this address from his id. The response  should be with saved address with id"() {
        setup:
        def addressData = [
                line1: "POSTfirstname",
                line2: "POSTlastname",
                city:  "POSTfirstname@globit.de",
                zipcode: "20146",
        ]
        def addressJsonData = new JsonBuilder(addressData).toPrettyString()



        when:

        def (postheaders, postsavedAddress) = httpClient.post(path: "/address",
                body: addressData,
                requestContentType: ContentType.JSON) { h, d -> [h, d] }
        //postsavedPerson
        def (headers, savedAddress)= httpClient.get(path: "/address/"+postsavedAddress.id,requestContentType: ContentType.JSON) {h,d -> [h,d]}


        then:
        assert postheaders
        assert postsavedAddress
        assert headers
        assert savedAddress

        headers.status == HttpStatus.SC_CREATED
        headers.contentType == "application/json"
        assert savedAddress.id
        savedAddress.zipcode == addressData.zipcode
    }


    /*
    **  Post Testing
     */

    def "save an address should respond with saved address with id"() {
        setup:
        def addressData = [
                line1: "POSTfirstname",
                line2: "POSTlastname",
                city:  "Hamburg",
                zipcode: "20146",
        ]
        def addressJsonData = new JsonBuilder(addressData).toPrettyString()

        when:

        def (headers, savedAddress) = httpClient.post(path: "/address",
                body: addressData, requestContentType: ContentType.JSON) { h, d -> [h, d] }

        then:
        assert headers
        assert savedAddress

        headers.status == HttpStatus.SC_CREATED
        headers.contentType == "application/json"
        assert savedAddress.id
        savedAddress.zipcode == addressData.zipcode

        /* json example
        json object: { "key": value, "key2": value }
        json array: ["item", 1, 2, 17.9, {"foo":1}, "last"]
        json  string: "example"
        json number: 123, 123.34
        {
            "firstname": "myfirstname"
        }
         */
    }

    /*
   ** Put Testing
    */

    def "update the address data"() {
        setup:
        def addressData = [
                line1: "Putfirstline",
                line2: "putsecondline",
                city:  "Hamburg",
                zipcode: "20146",
        ]


        def updatedaddressData = [
                line1: "Putfirstline22",
                line2: "putsecondline22",
                city:  "Hamburg",
                zipcode: "20146",
        ]

        when:

        //post an address
        def (postheaders, postsavedAddress) = httpClient.post(path: "/address",
                body: addressData,
                requestContentType: ContentType.JSON) { h, d -> [h, d] }

        //update the address data
        def response = httpClient.put(path: "/address/"+postsavedAddress.id, body: updatedaddressData,
                requestContentType: ContentType.JSON)


        then:
        response.status == HttpStatus.SC_CREATED
        response.data.id == postsavedAddress.id
        response.data.line1 == updatedaddressData.line1
        response.data.line2 == updatedaddressData.line2

    }







}
