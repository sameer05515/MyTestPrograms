const express = require('express');
const path = require('path');
const { loadWords } = require('./xml-loader');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.static(path.join(__dirname, '..', 'public')));

app.get('/api/words', async (req, res, next) => {
  try {
    const words = await loadWords();
    res.json(words);
  } catch (error) {
    next(error);
  }
});

app.use((error, req, res, next) => {
  // eslint-disable-next-line no-console
  console.error(error);
  res.status(500).json({ message: 'Failed to load vocabulary list.' });
});

app.listen(PORT, () => {
  // eslint-disable-next-line no-console
  console.log(`Server listening on http://localhost:${PORT}`);
});

