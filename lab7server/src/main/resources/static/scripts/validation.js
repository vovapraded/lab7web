document.getElementById('insertButton').addEventListener('click', function() {
    document.getElementById('insertModal').style.display = 'block';
});

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

$('#myForm').on('submit', function(event) {
    event.preventDefault(); // Остановка стандартного поведения формы
    let form = $(this);
    let errors = {};
    $('.error-message').text('');

    // Удаление класса error-highlight у полей без ошибок
    form.find('input, select').each(function() {
        let labelElement = $(`label[for='${this.id}']`);
        labelElement.removeClass('error-highlight');
    });

    // Клиентская валидация (пример)
    let isValid = true;
    var maybeNull = ['discount', 'refundable', 'venueType', 'venueCapacity'];
    var maybeNegativeOrZero = ['x', 'y'];

    form.find('input, select').each(function() {
        // Проверка на null
        if (!maybeNull.includes(this.id)){
            if (!this.value || this.value.length === 0){
                isValid = false;
                errors[this.id] = "Поле не заполнено";
                return;
            }
        } else {
            return;
        }

        // Проверка на позитивность
        if (this.type === 'number' && !maybeNegativeOrZero.includes(this.id)){
            if (this.value <= 0){
                isValid = false;
                errors[this.id] = "Поле " + this.id + " должно быть положительно";
                return;
            }
        } else if (this.type === 'number'){
            if (this.id === 'y' && this.value <= -618){
                isValid = false;
                errors[this.id] = "Поле должно быть больше -618";
                return;
            }
        }

        // Проверка что дата не будущее
        if (this.type === 'date'){
            let currentDate = new Date();
            let selectedDate = new Date(this.value);
            if (selectedDate > currentDate) {
                isValid = false;
                errors[this.id] = "Выбранная дата должна быть не позже текущей даты";
            }
        }

        // Проверка на пустую строку
        if (typeof this.value === 'string') {
            if (isBlank(this.value)){
                isValid = false;
                errors[this.id] = "Поле не может состоять из пробелов";
            }
        }
    });

    // Отображение ошибок
    Object.keys(errors).forEach(function(key) {
        let errorMessage = errors[key];
        let labelElement = $(`label[for='${key}']`);
        let errorElement = $(`#${key}-error`);

        // Добавление класса error-highlight
        labelElement.addClass('error-highlight');
        errorElement.text(errorMessage);
    });

    if (isValid) {
        // Отправка формы на сервер для проверки серверной валидации
        $.ajax({
            url: form.attr('action'),
            type: 'POST',
            data: form.serialize(),

            success: function(response) {
                window.location.href = '/tickets';
            },
            error: function(xhr, status, error) {
                // Обработка ошибки ответа
                // Обработка ошибки ответа
                var responseErrors = xhr.responseJSON;

                // Отображение ошибок на странице
                var errorMessage = '';
                Object.keys(responseErrors).forEach(function(key) {
                    errorMessage += responseErrors[key] + '. ';
                });
                $('#general-error').text(errorMessage);
            }
        });
    }
});
