package globit.sandbox.address

class AddressStorage {
    Map addressMap = [:]

    String generateid(){

        UUID.randomUUID()
    }
    void save(Address address){

            if (!address.id) {
                address.id = generateid()
            }
            addressMap.put(address.id, address)



    }

    void delete(Address address){
        if(address.id){
            addressMap.remove(address.id,address)
            address.id = null
        }
    }

    void delete(String id) {
        if (addressMap.containsKey(id)) {
            Address address = addressMap.get(id)
            addressMap.remove(id, address)
            address.id = null
        }
    }

    Map findAll(){
        return addressMap
    }

    Address find(String id){
        addressMap.get(id)
    }
}