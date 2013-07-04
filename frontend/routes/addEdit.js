module.exports = function(parent, options) {
	var api = options.api;
	
	var emptyObject = {
		id: "",
		name: "",
		description: "",
		firstImpression: "",
		aliases: [],
		tags: [],
		firstName: "",
		lastName: "",
		action: ""
	}
	
	var pageData = {}
	
	parent.get("/edit/:kind/:id", function(req, res) {
		if (req.session.sessionId) {
			pageData.obj = req.session.obj;
			pageData.kind = req.kind;
			pageData.displayKind = req.kind.charAt(0).toUpperCase() + req.kind.slice(1);
			pageData.session = req.session.json;
			pageData.action = "doEdit";
			pageData.displayAction = "Edit";
			res.render("addedit/" + req.kind, pageData);
		} else {
			res.send("Not authorized!");
		}
	});
	
	parent.get("/add/:kind", function(req, res, next) {
		if (req.session.sessionId) {
			pageData.obj = emptyObject;
			pageData.kind = req.kind;
			pageData.displayKind = req.kind.charAt(0).toUpperCase() + req.kind.slice(1);
			pageData.session = req.session.json;
			pageData.action = "doAdd";
			pageData.displayAction = "Add";
			res.render("addedit/" + req.kind, pageData);
		} else {
			res.send("Not authorized!");
		}
		
	});
	
	parent.post("/doEdit/:kind/:id", function(req, res) {
		if (req.session.sessionId) {
			api.postData(
					"create",
					"sessionId=" + req.session.sessionId + "&kind=" + req.kind + "&id=" + req.session.obj.id,
					JSON.stringify(req.body),
					function(resp) {
						res.redirect("/");
					},
					function(error) {
						res.redirect("/?msg=error");
					}
			);
		} else {
			res.send("Not authorized!");
		}
	});
	
	parent.post("/doAdd/:kind", function(req, res) {
		if (req.session.sessionId) {
			api.postData(
					"create",
					"sessionId=" + req.session.sessionId + "&kind=" + req.kind,
					JSON.stringify(req.body),
					function(resp) {
						res.redirect("/");
					},
					function(error) {
						res.redirect("/?msg=error");
					}
			);
		} else {
			res.send("Not authorized!");
		}
	});
}