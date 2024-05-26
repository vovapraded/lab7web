
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
        const newUrl = `${window.location.origin}/tickets?page=${pageNumber}`;
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
});
