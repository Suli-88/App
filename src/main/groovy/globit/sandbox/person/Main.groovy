package globit.sandbox.person

import globit.sandbox.address.Address

class Main {
   Person person = new Person(firstname:"myfirstname", lastname:"mysecondname")
    Address address = new Address(main:true,city:"Hamburg",line1:"Amm Akku",line2:"7")
    void main(){
        person.addAddress(address)

    }



}
