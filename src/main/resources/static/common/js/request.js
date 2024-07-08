document.addEventListener('DOMContentLoaded', () => {
    const nutrientUnits = {
        '에너지': 'kcal',
        '수분': 'g',
        '단백질': 'g',
        '지방': 'g',
        '회분': 'g',
        '탄수화물': 'g',
        '당류': 'g',
        '식이섬유': 'g',
        '포화지방산': 'g',
        '트랜스지방산': 'g',
        '자당': 'g',
        '포도당': 'g',
        '과당': 'g',
        '유당': 'g',
        '맥아당': 'g',
        '부티르산': 'g',
        '카프로산': 'g',
        '카프르산': 'g',
        '라우르산': 'g',
        '미리스트산': 'g',
        '팔미트산': 'g',
        '스테아르산': 'g',
        '아라키드산': 'g',
        '미리스톨레산': 'g',
        '팔미톨레산': 'g',
        '올레산': 'g',
        '박센산': 'g',
        '가돌레산': 'g',
        '레놀레산': 'g',
        '알파 리놀렌산': 'g',
        '감마 리놀렌산': 'g',
        '에이코사디에노산': 'g',
        '아라키돈산': 'g',
        '에이코사트리에노산': 'g',
        '에이코사펜타에노산': 'g',
        '도코사펜타에노산': 'g',
        '도코사헥사에노산': 'g',
        '트랜스 올레산': 'g',
        '트랜스 리놀레산': 'g',
        '트랜스 리놀레산': 'g',
        '칼슘': 'mg',
        '철': 'mg',
        '인': 'mg',
        '칼륨': 'mg',
        '나트륨': 'mg',
        '티아민': 'mg',
        '리보플라빈': 'mg',
        '티아민': 'mg',
        '리보플라빈': 'mg',
        '니아신': 'mg',
        '비타민C': 'mg',
        '콜레스테롤': 'mg',
        '마그네슘': 'mg',
        '아연': 'mg',
        '구리': 'mg',
        '망간': 'mg',
        '토코페롤': 'mg',
        '토코트리에놀': 'mg',
        '총 아미노산': 'mg',
        '이소류신': 'mg',
        '류신': 'mg',
        '라이신': 'mg',
        '메티오닌': 'mg',
        '페닐알라닌': 'mg',
        '트레오닌': 'mg',
        '발린': 'mg',
        '히스티딘': 'mg',
        '아르기닌': 'mg',
        '티로신': 'mg',
        '시스테인': 'mg',
        '알라닌': 'mg',
        '아스파르트산': 'mg',
        '글루탐산': 'mg',
        '글리신': 'mg',
        '프롤린': 'mg',
        '세린': 'mg',
        '카페인': 'mg',
        '비타민': 'μg',
        '레티놀': 'μg',
        '베타카로틴': 'μg',
        '비타민D': 'μg',
        '셀레늄': 'μg',
        '엽산': 'μg',
        '비타민 B12': 'μg'
    };

    function addNutrient() {
        const template = document.getElementById('nutrient-template');
        const clone = template.cloneNode(true);
        clone.classList.remove('template');
        clone.id = '';

        const container = document.getElementById('nutrients-container');
        const index = container.children.length;

        const selects = clone.querySelectorAll('select');
        const inputs = clone.querySelectorAll('input');

        selects.forEach(select => {
            select.name = select.name.replace('nutrients[0]', 'nutrients[' + index + ']');
            select.selectedIndex = 0; // 첫번째 영양소를 기본값으로
        });

        inputs.forEach(input => {
            input.name = input.name.replace('nutrients[0]', 'nutrients[' + index + ']');
            input.value = ''; // 일단 빈값으로
        });

        // 단위 설정
        const unitElement = clone.querySelector('#nutrient-unit');
        const selectedNutrient = selects[0].options[selects[0].selectedIndex].text;
        unitElement.textContent = nutrientUnits[selectedNutrient] || '';

        selects[0].addEventListener('change', function() {
            const selectedNutrient = this.options[this.selectedIndex].text;
            unitElement.textContent = nutrientUnits[selectedNutrient] || '';
        });

        container.appendChild(clone);
    }

    // 초기 단위 설정
    const templateSelect = document.querySelector('#nutrient-template select');
    const templateUnitElement = document.querySelector('#nutrient-template #nutrient-unit');

    templateSelect.addEventListener('change', function() {
        const selectedNutrient = this.options[this.selectedIndex].text;
        templateUnitElement.textContent = nutrientUnits[selectedNutrient] || '';
    });

    const initialNutrient = templateSelect.options[templateSelect.selectedIndex].text;
    templateUnitElement.textContent = nutrientUnits[initialNutrient] || '';

    window.addNutrient = addNutrient;
});