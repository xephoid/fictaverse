module.exports = function(parent, options) {
	var api = options.api;
	
	parent.param("kind", function(req, res, next, kind) {
		if (req.kind = kind) {
			next();
		} else {
			next(new Error('failed to find kind!'));
		}
	});
	
	parent.get("/list/:kind", function(req, res) {
		if (req.session.sessionId) {
			api.makeRequest(
				"GET",
				"findAllByWorld",
				"sessionId=" + req.session.sessionId + "&kind=" + req.kind,
				function(data) {
					req.session.json.activeKind = req.kind;
					res.send(JSON.stringify(data));
				},
				function (error) {
					console.log("Error getting data list!");
					console.log(error);
					res.send(JSON.stringify(error));
			});
		} else {
			res.send("Not authorized!");
		}
	});
}