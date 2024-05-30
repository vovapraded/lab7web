
document.addEventListener("DOMContentLoaded", function() {
    // Определение текущей страницы (предположим, что текущая страница хранится в переменной currentPage)
    const urlParams = new URLSearchParams(window.location.search);

// Получение значения параметра "page" из URL
    let currentPage = urlParams.get('page');
    if (!currentPage){
        currentPage = 1;
    }
    console.log(currentPage)
    // Функция для обработки нажатий на кнопки
    function handlePageClick(event) {
        const pageNumber = event.target.getAttribute('data-page');
// Получение текущего URL
        const currentUrl = new URL(window.location.href);

// Установка нового значения параметра "page"
        currentUrl.searchParams.set('page', pageNumber);

// Формирование нового URL с измененным параметром "page"
        const newUrl = currentUrl.toString();
        window.location.href = newUrl;
    }

    // Привязываем обработчик события к каждой кнопке
    const buttons = document.querySelectorAll('.pagination-buttons button');
    buttons.forEach(button => {
        const pageNumber = button.getAttribute('data-page');
        if (pageNumber == currentPage) {
            button.classList.add('current-page'); // Добавляем класс текущей страницы
        }
        button.addEventListener('click', handlePageClick);
    });
    const resetButton = document.getElementById('resetButton');

    resetButton.addEventListener('click', function(event) {
        event.preventDefault();  // Предотвратить сброс формы по умолчанию

        // Отправка запроса на сервер для удаления объекта фильтрации из сессии
        fetch('/tickets/resetFilter', { method: 'POST' })
            .then(response => {
                if (response.ok) {
                    // Сброс формы после успешного удаления фильтра из сессии
                    document.getElementById('filterForm').reset();
                    // Перезагрузить страницу, чтобы обновить данные
                    const urlParams = new URLSearchParams(window.location.search);
                    window.location.href = `/tickets?${urlParams}`;
                } else {
                    console.error('Failed to reset filter');
                }
            });
    });
    document.getElementById("filterButton").addEventListener("click", function() {
        // Получение формы
        var form = document.getElementById("filterForm");

        // Создание новой FormData из данных формы
        var formData = new FormData(form);

        // Отправка POST-запроса с помощью fetch
        fetch(`/tickets/applyFilter?${urlParams}`, {
            method: "POST",
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    // Перенаправление на другую страницу или контроллер
                    window.location.href = `/tickets?${urlParams}`;
                    console.log("Фильтр применен");
                } else {
                    // Обработка ошибки
                    console.error("Ошибка при отправке формы");
                }
            })
            .catch(error => {
                // Обработка ошибки сети или других проблем
                console.error("Произошла ошибка:", error);
            });

        // // Отправка POST запроса с данными фильтрации в виде JSON
        // fetch('/tickets/applyFilter', {
        //     method: 'POST',
        //     headers: {
        //         'Content-Type': 'application/json' // Указываем тип контента как JSON
        //     },
        //     body: JSON.stringify(formDataObj) // Преобразуем данные в JSON и отправляем
        // })
        //     .then(response => {
        //         if (response.ok) {
        //             // Обработка успешного ответа
        //             console.log("Filter applied successfully!");
        //             const urlParams = new URLSearchParams(window.location.search);
        //             window.location.href = `/tickets?${urlParams}`;
        //
        //             // Дополнительные действия, если необходимо
        //         } else {
        //             // Обработка ошибки
        //             console.error("Failed to apply filter");
        //         }
        //     })
        //     .catch(error => {
        //         console.error("Error:", error);
        //     });
    });
        const dropdownButton = document.querySelector('.dropbtn');
        const dropdownContent = document.querySelector('.dropdown-content');

        dropdownButton.addEventListener('click', function() {
            dropdownContent.classList.toggle('show');
        });

        // Закрываем меню, если пользователь кликнет вне его
        window.addEventListener('click', function(event) {
            if (!event.target.matches('.dropbtn')) {
                if (dropdownContent.classList.contains('show')) {
                    dropdownContent.classList.remove('show');
                }
            }
        });

        // Добавляем обработчики событий для триггеров
        document.getElementById("trigger1").addEventListener("click", function() {
            alert("Trigger 1 clicked");
        });

        document.getElementById("trigger2").addEventListener("click", function() {
            alert("Trigger 2 clicked");
        });

        document.getElementById("trigger3").addEventListener("click", function() {
            alert("Trigger 3 clicked");
        });


});
// document.addEventListener("DOMContentLoaded", function() {
//
// });
