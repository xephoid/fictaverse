module.exports = function(parent, options) {
	parent.get("/logout", function(req, res) {
		req.session.destroy(function() {
			res.redirect("/");
		});
	});
}