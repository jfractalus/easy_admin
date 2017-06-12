
    $(document).ready(function() {
        $('.date-picker').datetimepicker({
            locale: 'ru',
            format: 'L'  // DD.MM.YYYY
        });
        $('.date-time-picker').datetimepicker({
            locale: 'ru' // DD.MM.YYYY HH:mm (default)
        });
        $('#submitForm').submit(function () {
            saveRecord(this.getAttribute('data-url'), this.getAttribute('data-item-id'), submitStatus);
            return false;
        });
        tinymce.init({
            selector:'textarea',
            language_url : '/js/lang/ru.js',
            plugins: [
                'advlist autolink lists link image charmap print preview hr anchor pagebreak',
                'searchreplace wordcount visualblocks visualchars code fullscreen',
                'insertdatetime media nonbreaking save table contextmenu directionality',
                'emoticons template paste textcolor colorpicker textpattern imagetools codesample toc'
            ],
            toolbar1: 'undo redo | insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
            toolbar2: 'print preview media | forecolor backcolor emoticons | codesample',
            image_advtab: true,
            theme: 'modern',
            setup: function(editor) {
                editor.on('change', function(e) {
                    editor.save();
                });
            }
        });
    });

    var loadAndShowModal = function (urlHtml, modalId) {
        var modalId = "#" + modalId;
        if($(modalId).length == 0) {    //it doesn't exist
            getHTMLasync(urlHtml, initializeModal, modalId);
        }else{
            showModal(modalId);
        }
    }
    var saveModalChecks = function (modalId ,selectedSubItems) {
        var generatedHtml = '';
        var target = $('.'+modalId +' .input.subitems');
        selectedSubItems.forEach(function(item, ic) {
            generatedHtml+=subItemView(modalId, item);
        });
        target.html(generatedHtml);
        selectedSubItems.forEach(function(item, ic) {       // needed the second cycle(setting json values via jquery.val())
            var decodedJson = $("<div/>").html(item.jsonViewRow).text();    // used only for decoding &lt,&gt,etc.
            $('#' + modalId + item.id).val(decodedJson);
        });

    }
    var subItemView = function (modalId, item) {   // Same as in editor "subitem"
        return '<div class="alert alert-info alert-subitem">'
            + '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'
            + item.shortD
            + '<input type="hidden" name="'+ modalId +'" id="'+modalId+item.id+'"/>'
            + '</div>';
    }

    var selectDropItem = function (groupName, item) {
        var generatedHtml = '<div class="alert alert-info alert-subitem">'
            + '<a href="#" class="close" data-dismiss="alert" aria-label="close" onclick="removeDropItem(\''+groupName+'\',\''+ item +'\')">×</a>'
            + item
            + '<input type="hidden" name="'+ groupName +'" value="'+item+'"/>'
            + '</div>';
        var input = $('.'+groupName +' .input.subitems');
        input.html(input.html()+generatedHtml);
        $('.' +groupName+ ' .dropdown-menu .' + item).remove();
    }

    var removeDropItem = function (groupName, item) {
        var generatedHtml = '<li class="'+item+'" onclick="selectDropItem(\''+groupName+'\',\''+item+'\')">'
            + '<a style="cursor: pointer">'
            + item
            + '</a>'
        '</li>';
        var dropList = $('#'+groupName);
        dropList.html(dropList.html() + generatedHtml);
    }

    var submitStatus = function(result){
        var statusText = "ок";
        if(result.status==200){
            $('#submitStatus').addClass('alert-success');
        }else{
            statusText = "возникла ошибка";
            $('#submitStatus').addClass('alert-danger');
        }
        $('#submitStatusText').text(statusText);
        showAlert('#submitStatus');
    };

    var saveSubEntityValue = function (nameValue, rootItem) {
        var isChecked = $(rootItem).is(':checked');
        var hiddenInput = $( 'input[name="'+nameValue+'"]');
        hiddenInput.val(isChecked);
    };