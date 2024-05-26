document.getElementById('openModal').addEventListener('click', function() {
    document.getElementById('modal').style.display = 'block';
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
    var maybeNull = ['discount', 'refundable', 'venueType','venueCapacity'];
    var maybeNegativeOrZero = ['x', 'y'];

    form.find('input, select').each(function() {

        //проверка на налл
        if (!maybeNull.includes(this.id)){
            console.log(this.id)

            if (!this.value || this.value.length === 0  ){
                    isValid = false
                    errors[this.id] = "Поле не заполнено";
                    return;
                }
            }
        else{
            return;
        }
            //проверка на позитивность
        if (this.type === 'number' && !maybeNegativeOrZero.includes(this.id)){
            console.log(this.id)

            if (this.value <= 0){
                isValid = false
                errors[this.id] = "Поле "+this.id+" должно быть положительно";
                return;
            }
            //проверка координат
        }else if (this.type === 'number'){
            console.log(this.id)
            if (this.id === 'y' && this.value <= -618){
                isValid = false
                errors[this.id] = "Поле должно быть больше -618";
                return;
            }

        }
        //проверка что дата не будущее
        if (this.type === 'date'){
// Получаем текущую дату
            let currentDate = new Date();
// Получаем значение поля даты
            let selectedDate = new Date(this.value);

// Сравниваем выбранную дату с текущей
            if (selectedDate > currentDate) {
                // Выводим сообщение об ошибке или выполняем нужные действия
                isValid = false;
                errors[this.id] = "Выбранная дата должна быть не позже текущей даты";
            }
        }
        //проверка на пустую строку
        if (typeof this.value === 'string') {
            if (isBlank(this.value)){
                isValid = false;
                errors[this.id] = "Поле не может состоять из пробелов";
            }
        }

    })
    // Отображение ошибок

    // Установка ошибок
    Object.keys(errors).forEach(function(key) {
        let errorMessage = errors[key];
        let labelElement = $(`label[for='${key}']`);
        let errorElement = $(`#${key}-error`);

        // Добавление класса error-highlight
        labelElement.addClass('error-highlight');
        console.log(labelElement.class)
        errorElement.text(errorMessage);

    });


    if (isValid) {
        // Отправка формы на сервер для проверки серверной валидации
        $.ajax({
            url: form.attr('action'),
            type: 'POST',
            data: form.serialize(),
            success: function(response) {
                if (!response.errors) {
                    // Закрытие модального окна при успешной валидации
                    $('#modal').hide();

                }

            }
        });
    }
});