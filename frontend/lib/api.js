module.exports = FictaAPI

function FictaAPI(host, port, http) {
	this.host = host;
	this.port = port;
	this.http = http;
	
	this.makeRequest = function(method, action, params, success, error) {
		var getParams = params;
		if (method == "POST") {
			getParams = "";
		}
		
		var options = {
				hostname: this.host,
				port: this.port,
				path: "/" +action+ "?" + params,
				method: method
		};
		
		if (method == "POST") {
			options['data'] = params;
		}

		var request = http.request(options, function(resp) {
			resp.on("error", function(stuff) {
				console.log("ERRROR!");
				console.log(stuff);
				error({kind: "error", msg: "Server Error!"});
			});
			
			var data = "";
			resp.on('data', function (chunk) {
		        data += chunk.toString();
		    });

			resp.on("end", function() {
		    	try {
					var json = JSON.parse(data);
					success(json);
				} catch(exception) {
					error({
						kind: "error",
						msg: "Internal server respose error!",
						details: exception,
						response: data
					});
				}
		    })

		});
		
		request.on("error", function(e) {
			console.log("problem with request: " + e.message);
			res.send({kind: "error", msg: "Server Error!"});
		});
		
		request.end();
	}
}