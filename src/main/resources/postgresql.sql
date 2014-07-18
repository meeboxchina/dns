create table IPv4 (id serial, registry varchar(15), cc varchar(2), recordtype varchar(10), start cidr, count int, receiveddate date, status varchar(50), extensions varchar(50));

Format:

	registry|cc|type|start|value|date|status[|extensions...]

Where:

	registry  	The registry from which the data is taken.
			For APNIC resources, this will be:

			    apnic

	cc        	ISO 3166 2-letter code of the organisation to
	                which the allocation or assignment was made. 
	                May also include the following non-ISO 3166
	                code: 
	                
			    AP 	- networks based in more than one 
			          location in the Asia Pacific region

	type      	Type of Internet number resource represented
			in this record. One value from the set of 
			defined strings:

			    {asn,ipv4,ipv6}

	start     	In the case of records of type 'ipv4' or
			'ipv6' this is the IPv4 or IPv6 'first
			address' of the	range.

			In the case of an 16 bit AS number, the
			format is the integer value in the range:
			
			    0 - 65535
			
			In the case of a 32 bit ASN,  the value is
			in the range:
			
			    0 - 4294967296
			    
		    	No distinction is drawn between 16 and 32
		    	bit ASN values in the range 0 to 65535.

	value     	In the case of IPv4 address the count of
			hosts for this range. This count does not 
			have to represent a CIDR range.

			In the case of an IPv6 address the value 
			will be the CIDR prefix length from the 
			'first address'	value of <start>.
			
			In the case of records of type 'asn' the 
			number is the count of AS from this start 
			value.

	date      	Date on this allocation/assignment was made
			by the RIR in the format:
			
			    YYYYMMDD

			Where the allocation or assignment has been
			transferred from another registry, this date
			represents the date of first assignment or
			allocation as received in from the original
			RIR.

			It is noted that where records do not show a 
			date of	first assignment, this can take the 
			0000/00/00 value.

    	status    	Type of allocation from the set:

                            {allocated, assigned}

                	This is the allocation or assignment made by 
                	the registry producing the file and not any
                	sub-assignment by other agencies.

   	extensions 	In future, this may include extra data that 
   			is yet to be defined.
