module.exports = function(parent, options) {
	var api = options.api;
	parent.param("kind", function(req, res, next, kind) {
	  if (req.kind = kind) {
	    next();
	  } else {
	    next(new Error('failed to find kind!'));
	  }
	});
	
	parent.get("/edit/:kind/:id", function(req, res) {
		res.render(req.kind + "AddEdit", { obj: {}, action: "doEdit", displayAction: "Edit"});
	});
	
	parent.get("/add/:kind", function(req, res, next) {
		res.render(req.kind + "AddEdit", { obj: {id: "", name: ""}, action: "doAdd", displayAction: "Add"});
	});
	
	parent.post("/doEdit/:kind/:id", function(req, res) {
		res.redirect("/");
	});
	
	parent.post("/doAdd/:kind", function(req, res) {
		res.redirect("/");
	});
}