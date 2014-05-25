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
            </customer>
}    
</customers>