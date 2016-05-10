package globit.sandbox.http

import globit.sandbox.address.AddressService
import globit.sandbox.address.Address
import globit.sandbox.person.Person
import globit.sandbox.person.PersonService
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import spark.Spark


class HttpMain {
    static void main(String[] args) {
        Spark.port(8080)

        PersonService personService = new PersonService()
        AddressService addressService = new AddressService()

        // http://localhost:8080/person/1
        Spark.get("/person/:id", { request, response ->
            def personId = request.params(":id")
            def person = personService.find(personId)
            def jsonBuilder = new JsonBuilder(person)

            response.header("Content-Type", "application/json")
            response.status(201)
            jsonBuilder.toPrettyString()
        })

        // POST
        Spark.post("/person", { request, response ->
            String personData = request.body()
            def jsonParser = new JsonSlurper()
            Person person = jsonParser.parseText(personData)

            personService.save(person)

            def jsonBuilder = new JsonBuilder(person)
            response.header("Content-Type", "application/json")
            response.status(201)
            jsonBuilder.toPrettyString()
        })

        // PUT
        Spark.put("/person/:id", {request, response ->
        def personId = request.params(":id")
            String personData = request.body()
            def jsonParser = new JsonSlurper()
            Person updateperson = jsonParser.parseText(personData)
            def person = personService.find(personId)    //the found person
            person.firstname = updateperson.firstname;
            person.lastname = updateperson.lastname;
            person.email = updateperson.email;
            personService.save(person)

            def jsonBuilder = new JsonBuilder(person)

            response.header("Content-Type","application/json")
            response.status(201)
            jsonBuilder.toPrettyString()

        })

        // DELETE
        Spark.delete("/person/:id", {request, response ->
        def personId = request.params(":id")
            def person = personService.find(personId)     // Find the person with the given id
            def jsonBuilder = new JsonBuilder(person)
            personService.delete(personId)

            response.header("Content-Type","application/json")
            response.status(204)
            jsonBuilder.toPrettyString()

        })


        //TODO POST/VALID




        // http://localhost:8080/address/1
        Spark.get("/address/:id", { request, response ->
            def addressId = request.params(":id")
            def address = addressService.find(addressId)
            def jsonBuilder = new JsonBuilder(address)

            response.header("Content-Type", "application/json")
            response.status(201)
            jsonBuilder.toPrettyString()
        })


        // POST
        Spark.post("/address", { request, response ->
            String addressData = request.body()
            def jsonParser = new JsonSlurper()
            Address address = jsonParser.parseText(addressData)

            addressService.save(address)

            def jsonBuilder = new JsonBuilder(address)
            response.header("Content-Type", "application/json")
            response.status(201)
            jsonBuilder.toPrettyString()
        })

        // put
        Spark.put("/address/:id", {request, response ->
            def addressId = request.params(":id")
            String addressData = request.body()
            def jsonParser = new JsonSlurper()
            Address updateaddress = jsonParser.parseText(addressData)
            def address = addressService.find(addressId)    //the found address
            address.line1 = updateaddress.line1;
            address.line2 = updateaddress.line2;
            address.zipcode = updateaddress.zipcode;
            address.city = updateaddress.city
            addressService.save(address)

            def jsonBuilder = new JsonBuilder(address)

            response.header("Content-Type","application/json")
            response.status(201)
            jsonBuilder.toPrettyString()

        })


        // DELETE
        Spark.delete("/address/:id", {request, response ->
            def addressId = request.params(":id")
            def address = addressService.find(addressId)     // Find the address with the given id
            def jsonBuilder = new JsonBuilder(address)
            addressService.delete(addressId)

            response.header("Content-Type","application/json")
            response.status(204)
            jsonBuilder.toPrettyString()

        })




    }
}
