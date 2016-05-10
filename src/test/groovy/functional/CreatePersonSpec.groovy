package functional

import groovy.json.JsonBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import spock.lang.Specification

class CreatePersonSpec  extends Specification {

    def httpClient = new RESTClient("http://127.0.0.1:8080", ContentType.JSON)


    /*
    **  Post Testing
     */

    def "save a person should respond with saved person with id"() {
        setup:
        def personData = [
            firstname: "POSTfirstname",
            lastname: "POSTlastname",
            email:  "POSTfirstname@globit.de",
        ]
        def personJsonData = new JsonBuilder(personData).toPrettyString()

        when:

        def (headers, savedPerson) = httpClient.post(path: "/person",
                body: personData, requestContentType: ContentType.JSON) { h, d -> [h, d] }

        then:
        assert headers
        assert savedPerson

        headers.status == HttpStatus.SC_CREATED
        headers.contentType == "application/json"
        assert savedPerson.id
        savedPerson.firstname == personData.firstname

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
    ** Get Testing
     */

    def "save a person and the get this person from his id. The response  should be with saved person with id"() {
        setup:
        def personData = [
                firstname: "GETSulaiman",
                lastname: "GETSulaiman",
                email:  "GETfirstname@globit.de",
        ]
        def personJsonData = new JsonBuilder(personData).toPrettyString()



        when:

        def (postheaders, postsavedPerson) = httpClient.post(path: "/person",
                body: personData,
                requestContentType: ContentType.JSON) { h, d -> [h, d] }
        //postsavedPerson
        def (headers, savedPerson)= httpClient.get(path: "/person/"+postsavedPerson.id,requestContentType: ContentType.JSON) {h,d -> [h,d]}


        then:
        assert postheaders
        assert postsavedPerson
        assert headers
        assert savedPerson

        headers.status == HttpStatus.SC_CREATED
        headers.contentType == "application/json"
        assert savedPerson.id
        savedPerson.firstname == personData.firstname
    }

    /*
    ** Put Testing
     */

    def "update the personal data"() {
        setup:
        def personData = [
                firstname: "PUTfirstname",
                lastname: "PUTlastname",
                email   : "PUTfirstname@globit.de",
        ]


        def updatedpersonData = [
                firstname: "firstname222",
                lastname: "lastname222",
                email   : "firstname222@globit.de",
        ]

        when:

        //post the person
        def (postheaders, postsavedPerson) = httpClient.post(path: "/person",
                body: personData,
                requestContentType: ContentType.JSON) { h, d -> [h, d] }

        //update the person data
        def response = httpClient.put(path: "/person/"+postsavedPerson.id, body: updatedpersonData,
                requestContentType: ContentType.JSON)


        then:
        response.status == HttpStatus.SC_CREATED
        response.data.id == postsavedPerson.id
        response.data.firstname == updatedpersonData.firstname
        response.data.lastname == updatedpersonData.lastname

    }



    /*
    ** Delete Testing
     */

    def "delete a person with a given id "() {
        setup:
        def personData = [
                firstname: "DELETEfirstname",
                lastname: "DELETElastname",
                email   : "DELETEfirstname@globit.de",
        ]



        when:

        def (postheaders, postsavedPerson) = httpClient.post(path: "/person",
                body: personData,
                requestContentType: ContentType.JSON) { h, d -> [h, d] }
        //postsavedPerson


        def response= httpClient.delete(path: "/person/"+postsavedPerson.id,requestContentType: ContentType.JSON)


        then:
        response.status == HttpStatus.SC_NO_CONTENT
        assert !response.data
    }




}
