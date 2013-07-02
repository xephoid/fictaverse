module.exports = function(parent, options) {
	var api = options.api;
	parent.param("kind", function(req, res, next, kind) {
	  if (req.kind = kind) {
	    next();
	  } else {
	    next(new Error('failed to find kind!'));
	  }
	});
	
	var emptyObject = {
		id: "",
		name: ""
	}
	
	var pageData = {}
	
	parent.get("/edit/:kind/:id", function(req, res) {
		pageData.obj = {};
		pageData.kind = req.kind;
		pageData.displayKind = req.kind.charAt(0).toUpperCase() + req.kind.slice(1);
		pageData.session = req.session.json;
		pageData.action = "doEdit";
		pageData.displayAction = "Edit";
		res.render("addedit/" + req.kind, pageData);
	});
	
	parent.get("/add/:kind", function(req, res, next) {
		pageData.obj = emptyObject;
		pageData.kind = req.kind;
		pageData.displayKind = req.kind.charAt(0).toUpperCase() + req.kind.slice(1);
		pageData.session = req.session.json;
		pageData.action = "doAdd";
		pageData.displayAction = "Add";
		res.render("addedit/" + req.kind, pageData);
	});
	
	parent.post("/doEdit/:kind/:id", function(req, res) {
		res.redirect("/");
	});
	
	parent.post("/doAdd/:kind", function(req, res) {
		console.log(req.body);
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
	});
}