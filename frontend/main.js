// Standard setup stuff
var express = require("express"),
	http = require("http"),
	FictaAPI = require('./lib/api');
var app = module.exports = express();
var api = new FictaAPI("localhost", 8080, http);

// log requests
app.use(express.logger("dev"));

app.engine(".html", require("ejs").__express);
app.set("views", __dirname + "/views");
app.set("view engine", "html");

app.use(express.bodyParser());
app.use(express.cookieParser("a3b21caea0a87df453d77d30af363baf"));
app.use(express.session());

// End standard setup stuff

app.param("kind", function(req, res, next, kind) {
	if (req.kind = kind) {
		next();
	} else {
		next(new Error('failed to find kind!'));
	}
});

app.param("id", function(req, res, next, id) { 
	api.makeRequest(
		"GET",
		"find",
		"sessionId=" + req.session.sessionId + "&kind=" + req.kind + "&id=" + id,
		function(data) {
			console.log(data);
			req.session.obj = data;
			next();
		},
		function(error) {
			console.log("Error getting single object!");
			console.log(error);
			next();
		}
	);
});

var routes = [
	"./routes/addEdit",
	"./routes/list",
	"./routes/index",
	"./routes/login",
	"./routes/logout"
];

require("./routes/addEdit")(app, { "api" : api });
require("./routes/list")(app, { "api" : api });
require("./routes/index") (app, { "api" : api });
require("./routes/login") (app, { "api" : api });
require("./routes/logout")(app, { "api" : api });

// Static file routing
app.use(express.static(__dirname + "/server_side"));
app.use(express.static(__dirname + "/server_side/js"));
app.use(express.static(__dirname + "/server_side/css"));
app.use(express.static(__dirname + "/server_side/img"));

//you may `app.use(app.router)` before or after these
//static() middleware. If placed before them your routes
//will be matched BEFORE file serving takes place. If placed
//after as shown here then file serving is performed BEFORE
//any routes are hit:
app.use(app.router);

//Start server listening on specified port
app.listen(3000);
console.log("Listening on port 3000");