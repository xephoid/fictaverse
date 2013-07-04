function SignInCtrl($scope) {
	$scope.signIn = function() {
		$("#errorMessage").html("");
		var req = $.ajax({
			url: "login",
			type: "POST",
			data : $("form").serialize(),
			dataType: "json",
			cache: false,
			error: function (xhr, ajaxOptions, thrownError) {
//				console.log(xhr);
//				console.log(thrownError);
//				console.log(ajaxOptions);
		    },
			success: function(data) {
				if (data.kind == "error") {
					$("#errorMessage").html(data.msg);
				} else if(data.kind == "redirect") {
					window.location = data.url;
				}
			}
		});
		
		$scope.password = '';
	}
}