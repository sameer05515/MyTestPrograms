const fs = require('fs/promises');
const path = require('path');
const { Parser } = require('xml2js');

const parser = new Parser({
  explicitArray: false,
  mergeAttrs: true,
  trim: true,
  explicitRoot: false,
});

const CACHE_TTL_MS = Number(process.env.WORD_CACHE_TTL_MS || 5 * 60 * 1000);
let cachedWords = null;
let cacheTimestamp = 0;

const xmlPath = path.join(__dirname, '..', 'khajana.xml');

async function parseXml() {
  const xmlContent = await fs.readFile(xmlPath, 'utf8');
  const parsed = await parser.parseStringPromise(xmlContent);
  const wordList = parsed?.['word-list']?.myword;
  if (!wordList) {
    return [];
  }

  const entries = Array.isArray(wordList) ? wordList : [wordList];

  return entries.map((entry, index) => {
    const wordData = entry.word || {};
    const wordText = typeof wordData === 'string' ? wordData : wordData._;
    const type = typeof wordData === 'object' ? wordData.type || null : null;

    const toArray = (value) => {
      if (!value) return [];
      if (Array.isArray(value)) return value;
      return [value];
    };

    const meanings = toArray(entry.meanings?.meaning).map((meaning) =>
      typeof meaning === 'string' ? meaning : meaning?._,
    );

    const examples = toArray(entry.examples?.example).map((example) =>
      typeof example === 'string' ? example : example?._,
    );

    return {
      id: index,
      word: (wordText || '').trim(),
      type: type || 'unknown',
      meanings: meanings.filter(Boolean).map((value) => value.trim()),
      examples: examples.filter(Boolean).map((value) => value.trim()),
    };
  });
}

async function loadWords(options = {}) {
  const forceRefresh = options.forceRefresh === true;
  const now = Date.now();

  if (!forceRefresh && cachedWords && now - cacheTimestamp < CACHE_TTL_MS) {
    return cachedWords;
  }

  cachedWords = await parseXml();
  cacheTimestamp = now;
  return cachedWords;
}

module.exports = {
  loadWords,
};

