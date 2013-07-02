module.exports = function(parent, options) {
	var api = options.api;
	parent.get("/", function(req, res) {
		if (!req.session.sessionId) {
			res.render("signin")
		} else {
			if (!req.session.kinds) {
				api.makeRequest(
						"GET",
						"getKinds",
						"sessionId=" + req.session.sessionId,
						function(data) {
							req.session.kinds = data.kinds;
							res.redirect("/");
						},
						function(error) {
							console.log("Error!");
							console.log(error);
							res.render("index", { session: req.session.json, kinds: [] });
						});
				//res.write("One sec...");
			} else {
				res.render("index", { session: req.session.json, kinds: req.session.kinds });
			}
		}
	});
}