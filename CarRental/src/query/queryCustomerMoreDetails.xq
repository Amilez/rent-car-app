<customers>
    {
    let $customerC := doc("output/output.xml")/carRental/customers/customer
    let $countCus :=  count($customerC)
    return <amount_customers> {$countCus}</amount_customers>
    
    }{
    for $customer in doc("output/output.xml")/carRental/customers/customer
    return 
            <customer>
                {$customer/id}
                {$customer/firstName}
                {$customer/surname}
                {$customer/phone}
                {
                let $id := $customer/id                    
                let $lease := doc("output/output.xml")/carRental/leases/lease
                let $countC := count($lease[customer = $id])                    
                return <rented_times>{$countC}</rented_times>
                } 
                <leases>                                        
                    {
                    let $id := $customer/id                    
                    for $lease in doc("output/output.xml")/carRental/leases/lease
                    where $lease/customer = $id                    
                    return $lease/id
                    }                     
                </leases>
                <cars>
                {   
                    let $idD := $customer/id
                    let $lease := doc("output/output.xml")/carRental/leases/lease
                    for $car in distinct-values($lease[customer = $idD]/car)
                    return <car>
                        <id>{$car}</id>
                        {doc("output/output.xml")/carRental/cars/car[id=$car]/numberPlate}                        
                        <rented_times>{                                                                   
                        let $countD := count($lease[customer = $idD and car = $car])                    
                        return $countD  

                        }</rented_times>
                    </car>

                }   
                </cars>
            </customer>
}    
</customers>