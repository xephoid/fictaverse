module.exports = function(parent, options) {
	var api = options.api;
	parent.post("/login", function(req, res) {
		api.makeRequest(
			"GET",
			"signin",
			"email="+req.body.email+ "&password=" + req.body.password,
			function(json) {
				if (json.kind == "session") {
					req.session.sessionId = json.id;
					req.session.json = json;
					res.send({kind: "redirect", url: "/"});
				} else {
					res.send(json);
				}
			},
			function(error) {
				res.send(error);
		});
	});
}