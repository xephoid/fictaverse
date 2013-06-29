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


//function TodoCtrl($scope) {
//    $scope.todos = [
//    {text:'learn angular', done:true},
//    {text:'build an angular app', done:false}];
//     
//    $scope.addTodo = function() {
//    $scope.todos.push({text:$scope.todoText, done:false});
//    $scope.todoText = '';
//    };
//     
//    $scope.remaining = function() {
//    var count = 0;
//    angular.forEach($scope.todos, function(todo) {
//    count += todo.done ? 0 : 1;
//    });
//    return count;
//    };
//     
//    $scope.archive = function() {
//    var oldTodos = $scope.todos;
//    $scope.todos = [];
//    angular.forEach(oldTodos, function(todo) {
//    if (!todo.done) $scope.todos.push(todo);
//    });
//    };
//}