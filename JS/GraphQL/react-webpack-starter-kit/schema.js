import {
	GraphQLSchema,
	GraphQLObjectType,
	GraphQLString,
	GraphQLList
} from 'graphql'

import fetch from 'node-fetch'

const PersonType = new GraphQLObjectType({
	name: 'Person',
	description: '...',
	fields: () => ({
		firstName: {
			type: GraphQLString,
			resolve: (person) => person.first_name
		},
		lastName: {
			type: GraphQLString,
			resolve: (person) => person.last_name
		},
		email: {type: GraphQLString},
		username: {type: GraphQLString},
		id: {type: GraphQLString},
		friends: {
			type: new GraphQLList(PersonType),
			resolve: (person) => 
		}
	})
})

const QueryType = new GraphQLObjectType({
	name: 'Query',
	description: '...',
	fields: () => ({
		person: {
			type: PersonType,
			args: {
				id: {type: GraphQLString}
			},
			resolve: (root, args) => getPersonByURL() 

		}
	})
})

export default new GraphQLSchema({
	query: QueryType
})
