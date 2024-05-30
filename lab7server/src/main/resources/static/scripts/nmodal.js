document.addEventListener("DOMContentLoaded", function () {
    // получаем ширину отображенного содержимого и толщину ползунка прокрутки
    const windowInnerWidth = document.documentElement.clientWidth;
    const scrollbarWidth = window.innerWidth - document.documentElement.clientWidth;

    // привязываем необходимые элементы
    const bodyElementHTML = document.getElementsByTagName("body")[0];
    const filterTriggers = document.querySelectorAll(".trigger");

    function bodyMargin() {
        // Устанавливаем отрицательное значение margin-right для body
        document.body.style.marginRight = `-${scrollbarWidth}px`;
    }

    // функция для открытия модального окна
    function openModal(modal) {
        modal.style.display = "block";

        // если размер экрана больше 1366 пикселей (т.е. на мониторе может появиться ползунок)
        if (windowInnerWidth >= 1366) {
            bodyMargin();
        }

        const modalActive = modal.querySelector('.modalActive');
        // позиционируем наше окно по середине, где 175 - половина ширины модального окна
        modalActive.style.left = "calc(50% - " + (175 - scrollbarWidth / 2) + "px)";
    }

    // функция для закрытия модального окна
    function closeModal(modal) {
        modal.style.display = "none";
        if (windowInnerWidth >= 1366) {
            bodyMargin();
        }
    }

    // событие нажатия на триггер открытия модального окна
    filterTriggers.forEach(trigger => {
        trigger.addEventListener("click", function () {
            const modalId = this.dataset.modalId;
            const modal = document.getElementById(modalId);
            if (modal) {
                openModal(modal);
            }
        });
    });

    // нажатие на крестик закрытия модального окна
    document.querySelectorAll('.modalClose').forEach(closeButton => {
        closeButton.addEventListener("click", function () {
            const modal = this.closest('.modalBackground');
            if (modal) {
                closeModal(modal);
            }
        });
    });

    // закрытие модального окна на зону вне окна, т.е. на фон
    document.querySelectorAll('.modalBackground').forEach(modal => {
        modal.addEventListener("click", function (event) {
            if (event.target === modal) {
                closeModal(modal);
            }
        });
    });
});
