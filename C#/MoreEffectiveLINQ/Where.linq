<Query Kind="Statements" />

var customers = new[] {

	new { name = "Annie", Email = "annie@test.com" },
	new { name = "Ben", Email = "" },
	new { name = "Lily", Email = "annie@test.com" },
	new { name = "Joel", Email = "annie@test.com" },
	new { name = "Sam", Email = "" }

};

// use where condition

foreach( var customer in customers.Where( c => !String.IsNullOrEmpty(c.Email))){
	Console.WriteLine("Sending email to {0}", customer.name);
}

