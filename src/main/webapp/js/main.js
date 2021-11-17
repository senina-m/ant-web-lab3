$(function(){
    $('#r-input .r_button').on('click', function(event) {
        //event.preventDefault();
        $(this).addClass('active');
        $('#r-input .r_button').not(this).removeClass('active');

        let r = $(this).attr("id").split('').pop();
        $('.hidden_r input[type=hidden]').val(r);
    })
})