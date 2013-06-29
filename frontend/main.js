// Standard setup stuff
var express = require('express');
var app = module.exports = express();
var http = require("http");

//log requests
app.use(express.logger('dev'));

app.engine('.html', require('ejs').__express);
app.set('views', __dirname + '/views');
app.set('view engine', 'html');

app.use(express.bodyParser());
app.use(express.cookieParser('secret key!'));
app.use(express.session());

// End standard setup stuff

app.get("/", function(req, res) {
	if (!req.session.sessionId) {
		res.render("signin")
	} else {
		res.render("index")
	}
	
//	res.render("index", {
//		"title" : "This is a Test",
//		"param1" : "something",
//		"param2" : {"errme": announce}
//	});
});

app.post("/login", function(req, res) {
	var options = {
			hostname: "localhost",
			port: 8080,
			path: "/signin?email="+req.body.email+ "&password=" + req.body.password,
			method: "GET"
	};

	var request = http.request(options, function(resp) {
		resp.on("error", function(stuff) {
			console.log("ERRROR!");
			console.log(stuff);
			res.send({kind: "error", msg: "Server Error!"});
		});
		resp.on("data", function (chunk) {
			try {
				var json = JSON.parse(chunk);
				if (json.kind == "session") {
					req.session.sessionId = json.id;
					res.send({kind: "redirect", url: "/"});
				} else {
					res.send(chunk);
				}
			} catch(exception) {
				res.send({kind: "error", msg: "Server Error!"});
			}
		});
	});

	request.on("error", function(e) {
		console.log("problem with request: " + e.message);
		res.send({kind: "error", msg: "Server Error!"});
	});
	
	request.end();
	
	//res.send({"json": "will this work?", "list": ["Jack", "Kate", "Sawyer", "Juliette"]});
});

app.get("/logout", function(req, res) {
	req.session.destroy(function() {
		res.redirect('/');
	});
});


// Static file routing
app.use(express.static(__dirname + '/server_side'));
app.use(express.static(__dirname + '/server_side/js'));
app.use(express.static(__dirname + '/server_side/css'));

//you may `app.use(app.router)` before or after these
//static() middleware. If placed before them your routes
//will be matched BEFORE file serving takes place. If placed
//after as shown here then file serving is performed BEFORE
//any routes are hit:
app.use(app.router);

//Start server listening on specified port
if (!module.parent) {
	app.listen(3000);
	console.log("Listening on port 3000");
}