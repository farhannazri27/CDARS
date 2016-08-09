$(document).ready(function() {
    //close alert box
    var $alert = $(".alert");
    if ($alert.length) {
        var alerttimer = window.setTimeout(function() {
            $alert.trigger("click");
        }, 5000);
        $alert.animate({}, 200)
                .click(function() {
                    window.clearTimeout(alerttimer);
                    $alert.animate({height: 'hide'}, 200);
                });
    }
});

