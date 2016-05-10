package globit.sandbox.person

import globit.sandbox.address.Address

class Person {
    String firstname
    String lastname
    String id
    String email

    List<Address> addresses = []


    void addAddress(Address address) {
        if (address.main){
            this.addresses.each {it.main = false}


        }
        this.addresses.add(address)
    }
}

