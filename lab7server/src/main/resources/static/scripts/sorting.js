function generateSortButtons(columnIndex) {
    return `
                <div class="sort-buttons">
                    <button class="sort-button" data-index="${columnIndex}" data-order="asc" onclick="toggleSortOrder(this)">
                        <svg viewBox="0 0 24 24"><polygon points="24 24 12 0 0 24"></polygon></svg>
                    </button>
                    <button class="sort-button" data-index="${columnIndex}" data-order="desc" onclick="toggleSortOrder(this)">
                        <svg viewBox="0 0 24 24"><polygon points="0 0 12 24 24 0"></polygon></svg>
                    </button>
                </div>
            `;
}

function updateSortButtonsState() {
    const urlParams = new URLSearchParams(window.location.search);
    const currentSortingBy = urlParams.getAll('sorting_by');
    console.log(typeof currentSortingBy); // "number"

    if (currentSortingBy) {
        console.log(currentSortingBy)
        currentSortingBy.toString().split(",").forEach(pair => {
            const [columnName, sortOrder] = pair.split('_');
            const td =  document.querySelector(`#ticketTable thead tr td[id="${columnName}"]`);
            if (td) {
                const buttons = td.querySelectorAll(`.sort-buttons button`);
                buttons.forEach(btn => {
                        if (btn.getAttribute("data-order") === sortOrder) {
                            btn.classList.add('active');
                        } else {
                            btn.classList.remove('active');
                        }
                    }
                );
                console.log(columnName)

            }
        });


    }
}
function insertSortButtons() {
    const headers = document.querySelectorAll('#ticketTable thead tr td');
    headers.forEach((header, index) => {
        if (index !== 2 && index !== 7) { // Пропускаем ячейки с координатами
            header.innerHTML += generateSortButtons(index);
        }
    });
    updateSortButtonsState();

}

function toggleSortOrder(button) {
    const sortButtons = document.querySelectorAll('.sort-button button'); // Получаем все кнопки в блоке
    if (!button.classList.contains('active')) {
        sortButtons.forEach(btn => btn.classList.remove('active')); // Сбрасываем класс 'active' для всех кнопок
        button.classList.toggle('active'); // Добавляем класс 'active' к текущей кнопке

    }else {
        sortButtons.forEach(btn => btn.classList.remove('active')); // Сбрасываем класс 'active' для всех кнопок
        button.classList.remove('active'); // Добавляем класс 'active' к текущей кнопке
    }
    const tdId = button.parentNode.parentNode.id; // Получаем id родительского td элемента
    if (!tdId) return; // Если id не определен, выходим из функции

    // Если кнопка активна и ее id не включен в массив, добавляем его
    updateSortingByParameter(tdId,button,button.classList.contains('active'))



    // Сохраняем обновленный массив в localStorage
}
function updateSortingByParameter(parameterName, button,isActive) {
    parameterName = parameterName.toString();
    const urlParams = new URLSearchParams(window.location.search);
    let currentSortingBy = urlParams.getAll('sorting_by');

    // Если параметр сортировки не существует, инициализируем его как пустой массив
    if (!currentSortingBy) {
        currentSortingBy = [];
    }

    // Проверяем, существует ли текущий параметр
    const parameterIndex = currentSortingBy.findIndex(param => param.startsWith(parameterName));
    console.log(parameterIndex,parameterName)

    if (parameterIndex === -1) {
        // Если параметр не существует, добавляем его в массив
        currentSortingBy.push(`${parameterName}_${button.getAttribute('data-order')}`);
    } else {
        if (isActive) {
            // Если параметр уже существует, обновляем его значение
            currentSortingBy.splice(parameterIndex, 1);
            currentSortingBy.push(`${parameterName}_${button.getAttribute('data-order')}`);
        }else{
            currentSortingBy.splice(parameterIndex, 1);
        }
    }

    // Удаляем все текущие параметры сортировки из URL
    urlParams.delete('sorting_by');

    // Добавляем обновленные параметры сортировки в URL
    currentSortingBy.forEach(param => urlParams.append('sorting_by', param));


    // Обновляем URL страницы
    window.location.href = window.location.pathname + '?' + urlParams.toString();
}



window.onload = function() {
    const urlParams = new URLSearchParams(window.location.search);
    if (!urlParams.has('sorting_by')) {
        urlParams.set('sorting_by', 'id_asc');
        window.history.replaceState(null, null, "?" + urlParams.toString());
    }
    insertSortButtons();
};

