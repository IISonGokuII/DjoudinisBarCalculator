// ============================================================
// DJOUDINI'S BAR CALCULATOR - MAIN APP LOGIC
// ============================================================

// ===== STATE =====
let currentPage = 'home';
let billItems = [];
let drunkMode = false;
let currentDrinkFilter = 'all';
let selectedColor = null;
let selectedGlass = null;
let tipPercent = 10;
let splitCount = 2;
let splitTip = 0;
let bacGender = 'm';
let bacDrinks = [];
let currentModalDrink = null;
let currentModalType = null;

// ===== INIT =====
document.addEventListener('DOMContentLoaded', () => {
  loadState();
  renderDrinkList();
  populatePriceCheckDropdown();
  renderBillItems();
  renderBacDrinks();
  updateBillSummaryHome();

  // Prefill split amount from bill if available
  if (billItems.length > 0) {
    const total = billItems.reduce((sum, item) => sum + item.price, 0);
    document.getElementById('splitAmount').value = total.toFixed(2);
  }

  // Prefill tip from bill
  if (billItems.length > 0) {
    const total = billItems.reduce((sum, item) => sum + item.price, 0);
    document.getElementById('tipAmount').value = total.toFixed(2);
    calculateTip();
  }

  renderBillDrinkList('cocktails');
});

// ===== NAVIGATION =====
function navigateTo(page) {
  // Hide all pages
  document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));

  // Map pages to nav items
  const pageMap = {
    'home': 'home',
    'rechnung': 'rechnung',
    'getraenke': 'getraenke',
    'kamera': 'kamera',
    'promille': 'promille',
    'preischeck': 'home',
    'trinkgeld': 'home',
    'teilen': 'home'
  };

  // Show page (for sub-pages, use their own page section)
  const pageEl = document.getElementById('page-' + page);
  if (pageEl) {
    pageEl.classList.add('active');
  }

  // Update nav
  document.querySelectorAll('.nav-item').forEach(item => item.classList.remove('active'));
  const navPage = pageMap[page] || 'home';
  const navItems = document.querySelectorAll('.nav-item');
  const navLabels = ['home', 'rechnung', 'getraenke', 'kamera', 'promille'];
  const navIdx = navLabels.indexOf(navPage);
  if (navIdx >= 0 && navItems[navIdx]) {
    navItems[navIdx].classList.add('active');
  }

  currentPage = page;
  window.scrollTo(0, 0);
}

// ===== DRINK LIST RENDERING =====
function renderDrinkList() {
  const container = document.getElementById('drinkList');
  const search = document.getElementById('drinkSearchInput').value.toLowerCase();

  let drinks = [];

  // Get filtered drinks
  if (currentDrinkFilter === 'all' || currentDrinkFilter === 'cocktails') {
    drinks = drinks.concat(COCKTAIL_DATABASE.map(d => ({ ...d, type: 'cocktail' })));
  }
  if (currentDrinkFilter === 'all' || currentDrinkFilter === 'bier') {
    drinks = drinks.concat(BEER_DATABASE.map(d => ({ ...d, type: 'beer' })));
  }

  // Filter by category
  if (!['all', 'cocktails', 'bier'].includes(currentDrinkFilter)) {
    drinks = [...COCKTAIL_DATABASE.map(d => ({ ...d, type: 'cocktail' })), ...BEER_DATABASE.map(d => ({ ...d, type: 'beer' }))];
    drinks = drinks.filter(d => d.category === currentDrinkFilter);
  }

  // Search filter
  if (search) {
    drinks = drinks.filter(d =>
      d.name.toLowerCase().includes(search) ||
      d.description.toLowerCase().includes(search) ||
      d.category.toLowerCase().includes(search) ||
      (d.ingredients && d.ingredients.some(i => i.toLowerCase().includes(search))) ||
      (d.brewery && d.brewery.toLowerCase().includes(search)) ||
      (d.region && d.region.toLowerCase().includes(search))
    );
  }

  container.innerHTML = drinks.map(d => `
    <div class="drink-card" onclick="openDrinkModal('${d.type}', ${d.id})">
      <span class="drink-emoji">${d.emoji}</span>
      <div class="drink-info">
        <div class="drink-name">${d.name}</div>
        <div class="drink-desc">${d.description}</div>
      </div>
      <div class="drink-meta">
        <div class="drink-price">${d.avgPrice.toFixed(2)}&euro;</div>
        <div class="drink-abv">${d.abv}% vol</div>
      </div>
    </div>
  `).join('');

  if (drinks.length === 0) {
    container.innerHTML = '<div class="bill-empty"><div class="empty-icon">🔍</div><p>Keine Getränke gefunden</p></div>';
  }
}

function filterDrinks() {
  renderDrinkList();
}

function setDrinkFilter(filter, btn) {
  currentDrinkFilter = filter;
  document.querySelectorAll('#drinkFilterTabs .filter-tab').forEach(t => t.classList.remove('active'));
  if (btn) btn.classList.add('active');
  document.getElementById('drinkSearchInput').value = '';
  renderDrinkList();
}

// ===== DRINK MODAL =====
function openDrinkModal(type, id) {
  const db = type === 'cocktail' ? COCKTAIL_DATABASE : BEER_DATABASE;
  const drink = db.find(d => d.id === id);
  if (!drink) return;

  currentModalDrink = drink;
  currentModalType = type;

  document.getElementById('modalEmoji').textContent = drink.emoji;
  document.getElementById('modalName').textContent = drink.name;
  document.getElementById('modalCategory').textContent = drink.category;
  document.getElementById('modalDesc').textContent = drink.description;
  document.getElementById('modalPrice').textContent = drink.avgPrice.toFixed(2) + '€';
  document.getElementById('modalAbv').textContent = drink.abv + '%';
  document.getElementById('modalCategory2').textContent = drink.category;

  // Price check
  document.getElementById('modalAvgPrice').textContent = drink.avgPrice.toFixed(2) + '€';
  document.getElementById('modalFairPrice').textContent = (drink.avgPrice * 1.15).toFixed(2) + '€';
  document.getElementById('modalRipoffPrice').textContent = (drink.avgPrice * 1.40).toFixed(2) + '€';

  // Ingredients (cocktails only)
  if (type === 'cocktail' && drink.ingredients) {
    document.getElementById('modalIngredientsSection').style.display = 'block';
    document.getElementById('modalIngredients').innerHTML = drink.ingredients.map(i =>
      `<span class="ingredient-tag">${i}</span>`
    ).join('');
    document.getElementById('modalBrewerySection').style.display = 'none';
  } else if (type === 'beer') {
    document.getElementById('modalIngredientsSection').style.display = 'none';
    document.getElementById('modalBrewerySection').style.display = 'block';
    document.getElementById('modalBrewery').textContent = drink.brewery || '';
    document.getElementById('modalRegion').textContent = drink.region || '';
  }

  // Reset price input
  document.getElementById('modalPriceInput').value = '';
  document.getElementById('modalPriceResult').className = 'price-result';
  document.getElementById('modalPriceResult').textContent = '';

  document.getElementById('drinkModal').classList.add('active');
}

function closeDrinkModal(event) {
  if (event && event.target !== event.currentTarget) return;
  document.getElementById('drinkModal').classList.remove('active');
  currentModalDrink = null;
}

function checkModalPrice() {
  if (!currentModalDrink) return;
  const input = parseFloat(document.getElementById('modalPriceInput').value);
  if (isNaN(input) || input <= 0) {
    document.getElementById('modalPriceResult').className = 'price-result';
    return;
  }

  const avg = currentModalDrink.avgPrice;
  const diff = ((input - avg) / avg) * 100;
  const resultEl = document.getElementById('modalPriceResult');

  if (diff <= 15) {
    resultEl.className = 'price-result fair';
    resultEl.textContent = `✅ Fairer Preis! (${diff > 0 ? '+' : ''}${diff.toFixed(0)}% vom Durchschnitt)`;
  } else if (diff <= 40) {
    resultEl.className = 'price-result medium';
    resultEl.textContent = `⚠️ Etwas teuer (+${diff.toFixed(0)}% über Durchschnitt)`;
  } else {
    resultEl.className = 'price-result ripoff';
    resultEl.textContent = `🚨 ABZOCKE! +${diff.toFixed(0)}% über Durchschnitt!`;
  }
}

// ===== BILL MANAGEMENT =====
function addToBill(name, price, emoji, abv) {
  billItems.push({
    id: Date.now(),
    name: name,
    price: price,
    emoji: emoji || '🍸',
    abv: abv || 0,
    time: new Date().toLocaleTimeString('de-DE', { hour: '2-digit', minute: '2-digit' })
  });
  saveState();
  renderBillItems();
  updateBillBadge();
  updateBillSummaryHome();
  showToast(`${emoji || '🍸'} ${name} hinzugefügt`);
}

function addModalDrinkToBill() {
  if (!currentModalDrink) return;
  const priceInput = document.getElementById('modalPriceInput').value;
  const price = priceInput ? parseFloat(priceInput) : currentModalDrink.avgPrice;
  addToBill(currentModalDrink.name, price, currentModalDrink.emoji, currentModalDrink.abv);
  closeDrinkModal({ target: document.getElementById('drinkModal'), currentTarget: document.getElementById('drinkModal') });
}

function removeBillItem(id) {
  billItems = billItems.filter(item => item.id !== id);
  saveState();
  renderBillItems();
  updateBillBadge();
  updateBillSummaryHome();
}

function clearBill() {
  if (billItems.length === 0) return;
  billItems = [];
  saveState();
  renderBillItems();
  updateBillBadge();
  updateBillSummaryHome();
  showToast('🗑️ Rechnung gelöscht');
}

function renderBillItems() {
  const container = document.getElementById('billItems');
  const totalSection = document.getElementById('billTotalSection');
  const clearBtn = document.getElementById('clearBillBtn');

  if (billItems.length === 0) {
    container.innerHTML = '<div class="bill-empty"><div class="empty-icon">🍸</div><p>Noch keine Getränke hinzugefügt.<br>Wähle oben ein Getränk aus!</p></div>';
    totalSection.style.display = 'none';
    clearBtn.style.display = 'none';
    return;
  }

  container.innerHTML = billItems.map(item => `
    <div class="bill-item">
      <div style="font-size: 1.5rem; margin-right: 12px;">${item.emoji}</div>
      <div class="bill-item-info">
        <div class="bill-item-name">${item.name}</div>
        <div class="bill-item-detail">${item.time}</div>
      </div>
      <div class="bill-item-price">${item.price.toFixed(2)}&euro;</div>
      <button class="bill-item-remove" onclick="removeBillItem(${item.id})">&times;</button>
    </div>
  `).join('');

  const subtotal = billItems.reduce((sum, item) => sum + item.price, 0);
  document.getElementById('billSubtotal').textContent = subtotal.toFixed(2) + ' €';
  document.getElementById('billCount').textContent = billItems.length;
  document.getElementById('billTotal').textContent = subtotal.toFixed(2) + ' €';
  totalSection.style.display = 'block';
  clearBtn.style.display = 'block';
}

function updateBillBadge() {
  const badge = document.getElementById('billBadge');
  if (billItems.length > 0) {
    badge.style.display = 'flex';
    badge.textContent = billItems.length;
  } else {
    badge.style.display = 'none';
  }
}

function updateBillSummaryHome() {
  const section = document.getElementById('billSummaryHome');
  const content = document.getElementById('billSummaryContent');

  if (billItems.length === 0) {
    section.style.display = 'none';
    return;
  }

  section.style.display = 'block';
  const total = billItems.reduce((sum, item) => sum + item.price, 0);
  content.innerHTML = `
    <div class="bill-total-row">
      <span class="bill-total-label">${billItems.length} Getränke</span>
      <span class="bill-total-amount" style="color: var(--gold-light); font-size: 1.2rem; font-weight: 800;">${total.toFixed(2)} &euro;</span>
    </div>
  `;
}

// ===== BILL DRINK LIST (Quick Add) =====
function renderBillDrinkList(type) {
  const container = document.getElementById('billDrinkList');
  const search = document.getElementById('billSearchInput').value.toLowerCase();

  let drinks = [];
  if (type === 'cocktails') {
    drinks = COCKTAIL_DATABASE.map(d => ({ ...d, drinkType: 'cocktail' }));
  } else if (type === 'bier') {
    drinks = BEER_DATABASE.map(d => ({ ...d, drinkType: 'beer' }));
  }

  if (search) {
    drinks = drinks.filter(d =>
      d.name.toLowerCase().includes(search) ||
      d.category.toLowerCase().includes(search)
    );
  }

  // Show max 20 items for performance
  const displayDrinks = drinks.slice(0, 20);

  container.innerHTML = displayDrinks.map(d => `
    <div class="drink-card" onclick="addToBill('${d.name.replace(/'/g, "\\'")}', ${d.avgPrice}, '${d.emoji}', ${d.abv})">
      <span class="drink-emoji">${d.emoji}</span>
      <div class="drink-info">
        <div class="drink-name">${d.name}</div>
        <div class="drink-desc">${d.category}</div>
      </div>
      <div class="drink-meta">
        <div class="drink-price">${d.avgPrice.toFixed(2)}&euro;</div>
        <div class="drink-abv">${d.abv}%</div>
      </div>
    </div>
  `).join('');

  if (displayDrinks.length < drinks.length) {
    container.innerHTML += `<div style="text-align: center; padding: 10px; color: var(--text-muted); font-size: 0.8rem;">... und ${drinks.length - displayDrinks.length} weitere. Nutze die Suche!</div>`;
  }
}

function switchQuickAdd(type) {
  document.querySelectorAll('#quickAddTabs .filter-tab').forEach(t => t.classList.remove('active'));
  event.target.classList.add('active');

  const customEntry = document.getElementById('customEntry');

  if (type === 'custom') {
    customEntry.style.display = 'block';
    document.getElementById('billDrinkList').style.display = 'none';
  } else {
    customEntry.style.display = 'none';
    document.getElementById('billDrinkList').style.display = 'flex';
    renderBillDrinkList(type);
  }
}

function filterBillDrinks() {
  const activeTab = document.querySelector('#quickAddTabs .filter-tab.active');
  const type = activeTab ? activeTab.textContent.toLowerCase() : 'cocktails';
  if (type !== 'eigenes') {
    renderBillDrinkList(type === 'bier' ? 'bier' : 'cocktails');
  }
}

function addCustomToBill() {
  const name = document.getElementById('customName').value.trim();
  const price = parseFloat(document.getElementById('customPrice').value);

  if (!name) {
    showToast('❌ Bitte einen Namen eingeben');
    return;
  }
  if (isNaN(price) || price <= 0) {
    showToast('❌ Bitte einen gültigen Preis eingeben');
    return;
  }

  addToBill(name, price, '🍸', 0);
  document.getElementById('customName').value = '';
  document.getElementById('customPrice').value = '';
}

// ===== PRICE CHECK =====
function populatePriceCheckDropdown() {
  const select = document.getElementById('priceCheckDrink');

  const cocktailGroup = document.createElement('optgroup');
  cocktailGroup.label = '🍹 Cocktails';
  COCKTAIL_DATABASE.forEach(d => {
    const opt = document.createElement('option');
    opt.value = `cocktail_${d.id}`;
    opt.textContent = `${d.emoji} ${d.name} (Ø ${d.avgPrice.toFixed(2)}€)`;
    cocktailGroup.appendChild(opt);
  });
  select.appendChild(cocktailGroup);

  const beerGroup = document.createElement('optgroup');
  beerGroup.label = '🍺 Biere';
  BEER_DATABASE.forEach(d => {
    const opt = document.createElement('option');
    opt.value = `beer_${d.id}`;
    opt.textContent = `${d.emoji} ${d.name} (Ø ${d.avgPrice.toFixed(2)}€)`;
    beerGroup.appendChild(opt);
  });
  select.appendChild(beerGroup);
}

function updatePriceCheck() {
  const drinkVal = document.getElementById('priceCheckDrink').value;
  const amount = parseFloat(document.getElementById('priceCheckAmount').value);
  const resultEl = document.getElementById('priceCheckResult');

  if (!drinkVal || isNaN(amount) || amount <= 0) {
    resultEl.className = 'price-result';
    return;
  }

  const [type, id] = drinkVal.split('_');
  const db = type === 'cocktail' ? COCKTAIL_DATABASE : BEER_DATABASE;
  const drink = db.find(d => d.id === parseInt(id));
  if (!drink) return;

  const diff = ((amount - drink.avgPrice) / drink.avgPrice) * 100;

  if (diff <= 15) {
    resultEl.className = 'price-result fair';
    resultEl.innerHTML = `✅ <strong>Fairer Preis!</strong><br>${drink.emoji} ${drink.name}: ${amount.toFixed(2)}€ ist ${diff > 0 ? '+' : ''}${diff.toFixed(0)}% vom Ø ${drink.avgPrice.toFixed(2)}€`;
  } else if (diff <= 40) {
    resultEl.className = 'price-result medium';
    resultEl.innerHTML = `⚠️ <strong>Etwas teuer!</strong><br>${drink.emoji} ${drink.name}: +${diff.toFixed(0)}% über dem Ø von ${drink.avgPrice.toFixed(2)}€`;
  } else {
    resultEl.className = 'price-result ripoff';
    resultEl.innerHTML = `🚨 <strong>ABZOCKE!</strong><br>${drink.emoji} ${drink.name}: +${diff.toFixed(0)}% über dem Ø von ${drink.avgPrice.toFixed(2)}€! Das sind ${(amount - drink.avgPrice).toFixed(2)}€ zu viel!`;
  }
}

// ===== TIP CALCULATOR =====
function setTip(percent) {
  tipPercent = percent;
  document.getElementById('tipPercent').value = percent;
  document.getElementById('tipPercentValue').textContent = percent + '%';
  document.querySelectorAll('#page-trinkgeld .tip-btn').forEach(btn => btn.classList.remove('active'));
  event.target.classList.add('active');
  calculateTip();
}

function updateTipSlider() {
  tipPercent = parseInt(document.getElementById('tipPercent').value);
  document.getElementById('tipPercentValue').textContent = tipPercent + '%';
  document.querySelectorAll('#page-trinkgeld .tip-btn').forEach(btn => {
    btn.classList.toggle('active', parseInt(btn.textContent) === tipPercent);
  });
}

function calculateTip() {
  const amount = parseFloat(document.getElementById('tipAmount').value);
  if (isNaN(amount) || amount <= 0) {
    document.getElementById('tipResult').style.display = 'none';
    document.getElementById('tipRoundUp').style.display = 'none';
    return;
  }

  const tip = amount * (tipPercent / 100);
  const total = amount + tip;

  document.getElementById('tipResult').style.display = 'block';
  document.getElementById('tipResultValue').textContent = tip.toFixed(2) + ' €';
  document.getElementById('tipResultTotal').textContent = `Gesamt: ${total.toFixed(2)} €`;

  // Round up suggestions
  const roundUpContainer = document.getElementById('roundUpButtons');
  const roundUps = [];
  for (let r = Math.ceil(total); r <= Math.ceil(total) + 10; r += 5) {
    if (r > total) roundUps.push(r);
  }
  // Also add nearest nice numbers
  const niceNumbers = [Math.ceil(total / 5) * 5, Math.ceil(total / 10) * 10];
  niceNumbers.forEach(n => {
    if (n > total && !roundUps.includes(n)) roundUps.push(n);
  });
  roundUps.sort((a, b) => a - b);
  const uniqueRoundUps = [...new Set(roundUps)].slice(0, 3);

  if (uniqueRoundUps.length > 0) {
    document.getElementById('tipRoundUp').style.display = 'block';
    roundUpContainer.innerHTML = uniqueRoundUps.map(r => `
      <button class="tip-btn" onclick="roundUpTo(${r})">${r.toFixed(0)} &euro;</button>
    `).join('');
  }
}

function roundUpTo(amount) {
  const original = parseFloat(document.getElementById('tipAmount').value);
  const tip = amount - original;
  const percent = (tip / original * 100).toFixed(0);

  document.getElementById('tipResultValue').textContent = tip.toFixed(2) + ' €';
  document.getElementById('tipResultTotal').textContent = `Gesamt: ${amount.toFixed(2)} € (${percent}% Trinkgeld)`;
  showToast(`💰 ${amount.toFixed(0)}€ - ${tip.toFixed(2)}€ Trinkgeld`);
}

// ===== BILL SPLITTER =====
function changeSplitCount(delta) {
  splitCount = Math.max(1, Math.min(20, splitCount + delta));
  document.getElementById('splitCount').textContent = splitCount;
  calculateSplit();
}

function setSplitTip(percent) {
  splitTip = percent;
  document.querySelectorAll('#page-teilen .tip-btn').forEach(btn => btn.classList.remove('active'));
  document.getElementById('splitTip' + percent).classList.add('active');
  calculateSplit();
}

function calculateSplit() {
  const amount = parseFloat(document.getElementById('splitAmount').value);
  if (isNaN(amount) || amount <= 0) {
    document.getElementById('splitResult').style.display = 'none';
    return;
  }

  const total = amount * (1 + splitTip / 100);
  const perPerson = total / splitCount;

  document.getElementById('splitResult').style.display = 'block';
  document.getElementById('splitResultValue').textContent = perPerson.toFixed(2) + ' €';
  document.getElementById('splitResultTotal').textContent = `Gesamt: ${total.toFixed(2)} € ÷ ${splitCount} Personen${splitTip > 0 ? ` (inkl. ${splitTip}% Trinkgeld)` : ''}`;
}

function useBillTotal() {
  if (billItems.length === 0) {
    showToast('❌ Keine Rechnung vorhanden');
    return;
  }
  const total = billItems.reduce((sum, item) => sum + item.price, 0);
  document.getElementById('splitAmount').value = total.toFixed(2);
  calculateSplit();
  showToast('📋 Betrag übernommen');
}

// ===== BAC CALCULATOR =====
function setGender(gender) {
  bacGender = gender;
  document.getElementById('genderM').classList.toggle('active', gender === 'm');
  document.getElementById('genderF').classList.toggle('active', gender === 'f');
  calculateBAC();
}

function updateBacHours() {
  const hours = document.getElementById('bacHours').value;
  document.getElementById('bacHoursValue').textContent = hours + 'h';
}

function showBacAddDrink() {
  document.getElementById('bacModal').classList.add('active');
}

function closeBacModal(event) {
  if (event && event.target !== event.currentTarget) return;
  document.getElementById('bacModal').classList.remove('active');
}

function addBacDrink(name, abv, ml) {
  bacDrinks.push({ name, abv, ml, id: Date.now() });
  closeBacModal({ target: document.getElementById('bacModal'), currentTarget: document.getElementById('bacModal') });
  renderBacDrinks();
  calculateBAC();
  showToast(`🍺 ${name} hinzugefügt`);
}

function addCustomBacDrink() {
  const abv = parseFloat(document.getElementById('bacCustomAbv').value);
  const ml = parseFloat(document.getElementById('bacCustomMl').value);

  if (isNaN(abv) || isNaN(ml) || abv <= 0 || ml <= 0) {
    showToast('❌ Bitte gültige Werte eingeben');
    return;
  }

  addBacDrink(`Custom (${abv}%, ${ml}ml)`, abv, ml);
  document.getElementById('bacCustomAbv').value = '';
  document.getElementById('bacCustomMl').value = '';
}

function removeBacDrink(id) {
  bacDrinks = bacDrinks.filter(d => d.id !== id);
  renderBacDrinks();
  calculateBAC();
}

function renderBacDrinks() {
  const container = document.getElementById('bacDrinksList');

  if (bacDrinks.length === 0) {
    container.innerHTML = '<div style="text-align: center; padding: 16px; color: var(--text-muted); font-size: 0.85rem;">Noch keine Getränke hinzugefügt</div>';
    return;
  }

  container.innerHTML = bacDrinks.map(d => `
    <div class="bill-item">
      <div style="font-size: 1.3rem; margin-right: 12px;">🍺</div>
      <div class="bill-item-info">
        <div class="bill-item-name">${d.name}</div>
        <div class="bill-item-detail">${d.abv}% vol · ${d.ml}ml</div>
      </div>
      <button class="bill-item-remove" onclick="removeBacDrink(${d.id})">&times;</button>
    </div>
  `).join('');
}

function calculateBAC() {
  const weight = parseFloat(document.getElementById('bacWeight').value) || 80;
  const hours = parseFloat(document.getElementById('bacHours').value) || 0;
  const resultSection = document.getElementById('bacResultSection');

  if (bacDrinks.length === 0) {
    resultSection.style.display = 'none';
    return;
  }

  resultSection.style.display = 'block';

  // Widmark formula
  // BAC = (alcohol in grams / (body weight in kg * r)) - (0.15 * hours)
  // r = 0.68 for men, 0.55 for women
  const r = bacGender === 'm' ? 0.68 : 0.55;

  // Calculate total alcohol in grams
  // alcohol (g) = volume (ml) * (abv/100) * 0.789 (density of ethanol)
  let totalAlcoholGrams = 0;
  bacDrinks.forEach(d => {
    totalAlcoholGrams += d.ml * (d.abv / 100) * 0.789;
  });

  // BAC in permille
  let bac = (totalAlcoholGrams / (weight * r)) - (0.15 * hours);
  bac = Math.max(0, bac);

  // Display
  document.getElementById('bacValue').textContent = bac.toFixed(2) + '‰';

  // BAC bar
  const fill = document.getElementById('bacFill');
  const percentage = Math.min(100, (bac / 2.0) * 100);
  fill.style.width = percentage + '%';

  fill.classList.remove('warning', 'danger');
  if (bac > 1.0) fill.classList.add('danger');
  else if (bac > 0.5) fill.classList.add('warning');

  // Status
  const status = document.getElementById('bacStatus');
  status.classList.remove('sober', 'tipsy', 'drunk', 'danger');

  if (bac === 0) {
    status.className = 'bac-status sober';
    status.textContent = '✅ Du bist nüchtern';
    document.getElementById('bacLabel').textContent = 'Nüchtern';
  } else if (bac < 0.3) {
    status.className = 'bac-status sober';
    status.textContent = '😊 Leicht angetrunken - kaum spürbar';
    document.getElementById('bacLabel').textContent = 'Leicht';
  } else if (bac < 0.5) {
    status.className = 'bac-status tipsy';
    status.textContent = '😏 Angetrunken - NICHT Autofahren!';
    document.getElementById('bacLabel').textContent = 'Angetrunken';
  } else if (bac < 0.8) {
    status.className = 'bac-status tipsy';
    status.textContent = '🥴 Betrunken - Reaktion eingeschränkt!';
    document.getElementById('bacLabel').textContent = 'Betrunken';
  } else if (bac < 1.5) {
    status.className = 'bac-status drunk';
    status.textContent = '😵 Stark betrunken - Sei vorsichtig!';
    document.getElementById('bacLabel').textContent = 'Stark betrunken';
  } else {
    status.className = 'bac-status danger';
    status.textContent = '🚨 GEFÄHRLICH - Bitte kein Alkohol mehr!';
    document.getElementById('bacLabel').textContent = 'Gefährlich!';
  }

  // Time to sober
  if (bac > 0) {
    const hoursToSober = bac / 0.15;
    const h = Math.floor(hoursToSober);
    const m = Math.round((hoursToSober - h) * 60);
    document.getElementById('bacSoberTime').textContent = `Geschätzte Zeit bis nüchtern: ~${h}h ${m}min`;
  } else {
    document.getElementById('bacSoberTime').textContent = '';
  }
}

// ===== CAMERA / PHOTO RECOGNITION =====
function takePhoto() {
  document.getElementById('cameraInput').click();
}

function pickFromGallery() {
  document.getElementById('fileInput').click();
}

function handleFileSelect(event) {
  const file = event.target.files[0];
  if (!file) return;

  const reader = new FileReader();
  reader.onload = function(e) {
    const img = document.getElementById('capturedImage');
    img.src = e.target.result;
    img.style.display = 'block';
    document.getElementById('cameraPlaceholder').style.display = 'none';
    document.getElementById('cameraPreview').style.display = 'none';
    document.getElementById('cameraContainer').classList.add('active');

    // Analyze image colors
    analyzeImageColors(img);
  };
  reader.readAsDataURL(file);
}

function analyzeImageColors(img) {
  // Create canvas for color analysis
  const canvas = document.createElement('canvas');
  const ctx = canvas.getContext('2d');

  img.onload = function() {
    canvas.width = 100;
    canvas.height = 75;
    ctx.drawImage(img, 0, 0, 100, 75);

    const imageData = ctx.getImageData(0, 0, 100, 75);
    const data = imageData.data;

    // Calculate average color
    let r = 0, g = 0, b = 0, count = 0;
    // Focus on center of image
    for (let y = 20; y < 55; y++) {
      for (let x = 25; x < 75; x++) {
        const idx = (y * 100 + x) * 4;
        r += data[idx];
        g += data[idx + 1];
        b += data[idx + 2];
        count++;
      }
    }

    r = Math.round(r / count);
    g = Math.round(g / count);
    b = Math.round(b / count);

    // Determine dominant color category
    const detectedColor = categorizeColor(r, g, b);
    if (detectedColor) {
      selectColor(detectedColor, null);
      // Auto-highlight the color option
      document.querySelectorAll('.color-option').forEach(opt => {
        opt.classList.toggle('selected', opt.dataset.color === detectedColor);
      });
      showToast(`🎨 Farbe erkannt: ${detectedColor}`);
    }
  };

  // Trigger onload for already loaded images
  if (img.complete) {
    img.onload();
  }
}

function categorizeColor(r, g, b) {
  const brightness = (r + g + b) / 3;

  if (brightness < 50) return 'dark';
  if (brightness > 220 && Math.abs(r - g) < 20 && Math.abs(g - b) < 20) return 'clear';
  if (r > 200 && g > 180 && b > 150 && brightness > 200) return 'white';

  // Dominant channel detection
  if (r > g + 40 && r > b + 40) {
    if (g > 100) return 'orange';
    return 'red';
  }
  if (g > r + 20 && g > b + 20) return 'green';
  if (b > r + 30 && b > g + 30) return 'blue';
  if (r > 150 && g > 100 && b < 100) return 'amber';
  if (r > 200 && g > 150 && b < 120) return 'yellow';
  if (r > 180 && b > 130 && g < 130) return 'pink';
  if (r > 100 && g > 60 && b < 60 && brightness < 140) return 'brown';

  return 'clear';
}

function selectColor(color, element) {
  selectedColor = color;
  document.querySelectorAll('.color-option').forEach(opt => opt.classList.remove('selected'));
  if (element) element.classList.add('selected');
}

function selectGlass(glass, element) {
  selectedGlass = glass;
  document.querySelectorAll('.glass-option').forEach(opt => opt.classList.remove('selected'));
  if (element) element.classList.add('selected');
}

function identifyDrink() {
  if (!selectedColor && !selectedGlass) {
    showToast('❌ Bitte wähle mindestens eine Farbe oder Glastyp');
    return;
  }

  let matches = [];
  const patterns = DRINK_VISUAL_PATTERNS;

  // Match by color
  if (selectedColor && patterns.cocktails[selectedColor]) {
    const colorMatches = patterns.cocktails[selectedColor];
    colorMatches.forEach(name => {
      const drink = COCKTAIL_DATABASE.find(d => d.name === name);
      if (drink) {
        const existing = matches.find(m => m.drink.id === drink.id);
        if (existing) {
          existing.score += 50;
        } else {
          matches.push({ drink, type: 'cocktail', score: 50 });
        }
      }
    });
  }

  // Match by glass type
  if (selectedGlass && patterns.glassTypes[selectedGlass]) {
    const glassMatches = patterns.glassTypes[selectedGlass];
    glassMatches.forEach(name => {
      const drink = COCKTAIL_DATABASE.find(d => d.name === name);
      if (drink) {
        const existing = matches.find(m => m.drink.id === drink.id);
        if (existing) {
          existing.score += 40;
        } else {
          matches.push({ drink, type: 'cocktail', score: 40 });
        }
      }
    });
  }

  // Sort by score
  matches.sort((a, b) => b.score - a.score);
  const topMatches = matches.slice(0, 5);

  // Display results
  const resultsContainer = document.getElementById('recognitionResults');
  const matchContainer = document.getElementById('matchResults');

  if (topMatches.length === 0) {
    matchContainer.innerHTML = '<div class="bill-empty"><div class="empty-icon">🤷</div><p>Kein passendes Getränk gefunden.<br>Versuche andere Kriterien!</p></div>';
  } else {
    matchContainer.innerHTML = topMatches.map(m => {
      const maxScore = 90;
      const confidence = Math.min(95, Math.round((m.score / maxScore) * 100));
      const confClass = confidence >= 60 ? 'high' : 'medium';

      return `
        <div class="match-card" onclick="openDrinkModal('${m.type}', ${m.drink.id})">
          <div class="match-confidence ${confClass}">${confidence}%</div>
          <div class="drink-info">
            <div class="drink-name">${m.drink.emoji} ${m.drink.name}</div>
            <div class="drink-desc">${m.drink.description}</div>
          </div>
          <div class="drink-meta">
            <div class="drink-price">${m.drink.avgPrice.toFixed(2)}€</div>
          </div>
        </div>
      `;
    }).join('');
  }

  resultsContainer.classList.add('active');
}

// ===== DRUNK MODE =====
function toggleDrunkMode() {
  drunkMode = !drunkMode;
  document.getElementById('drunkToggle').classList.toggle('active', drunkMode);

  if (drunkMode) {
    document.documentElement.style.fontSize = '20px';
    showToast('🍻 Betrunken-Modus AN - Große Buttons!');
  } else {
    document.documentElement.style.fontSize = '16px';
    showToast('Betrunken-Modus AUS');
  }
}

// ===== TOAST NOTIFICATIONS =====
function showToast(message) {
  const toast = document.getElementById('toast');
  toast.textContent = message;
  toast.classList.add('show');

  setTimeout(() => {
    toast.classList.remove('show');
  }, 2500);
}

// ===== LOCAL STORAGE =====
function saveState() {
  try {
    localStorage.setItem('djoudini_bill', JSON.stringify(billItems));
    localStorage.setItem('djoudini_bacDrinks', JSON.stringify(bacDrinks));
  } catch (e) {
    // Storage full or not available
  }
}

function loadState() {
  try {
    const savedBill = localStorage.getItem('djoudini_bill');
    if (savedBill) billItems = JSON.parse(savedBill);

    const savedBac = localStorage.getItem('djoudini_bacDrinks');
    if (savedBac) bacDrinks = JSON.parse(savedBac);
  } catch (e) {
    // No saved state
  }
  updateBillBadge();
}

// ===== SERVICE WORKER REGISTRATION =====
if ('serviceWorker' in navigator) {
  navigator.serviceWorker.register('sw.js').catch(() => {});
}
