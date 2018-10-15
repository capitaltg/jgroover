# JGroover
JGroover is a simple, embeddable JSON server that supports a stripped-down REST
API for returning JSON results.  We use JGroover to simulate external data
sources and have added some basic features as needed (time delays, error rates,
wildcard searches, etc.).

## Gradle
You can import JGroover using:

`compile 'com.capitaltg:jgroover:<version>'`

## Using JGroover
You can embed JGroover in your application with:
```
JGrooverServer server
  = new JGrooverServer(3030, 'classpath:some-test-data.json');
server.setAverageTimeDelay(1000);
server.startServer();
```

## Usage
JGroover requires one JSON file as input that contains one or more object types.
Each object type contains an array of JSON objects that can be queried with an
http GET operation that contains zero or more query parameters.  Each parameter
will be matched against objects of the give type.  For example, if you use a
JSON file containing the following:

```
{
  "vehicles": [
    {
      "id": 3698726572,
      "vin": "52524AB25FG121",
      "licensePlate": "1BR9534",
      "make": "Ford",
      "model": "Focus",
      "year": 2002
    },
    {
      "id": 4037379007,
      "vin": null,
      "licensePlate": "7654321",
      "make": "Hyundai",
      "model": "Sonata",
      "year": 2012
    }
  ],
  "people": [
    {
      "id": 1928172639,
      "firstName": "Geoffrey",
      "middleName": "Alexander",
      "lastName": "Huntoon",
      "identifiers": [
        {
          "ssn": "123-12-1234"
        }
      ]
    }
  ]
}  
```

You can get all vehicles using:

`curl "localhost:5050/api/vehicles"`

You can get all vehicles where Make=Ford using

`curl "localhost:5050/api/vehicles?make=ford"`

Query parameters are case-sensitive, but values are not.  JGroover supports
using wildcards.  Using wildcards, you can return all vehicles with a model
ending in `nata` using:

`curl "localhost:5050/api/vehicles?model=*nata"`



## Contribute

If you need or add a feature you think may benefit others, feel free to open an
issue or fork the repository and create a pull request.
