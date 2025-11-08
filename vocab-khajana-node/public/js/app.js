document.addEventListener('alpine:init', () => {
  Alpine.data('wordList', () => ({
    words: [],
    query: '',
    typeFilter: 'all',
    loading: true,
    error: '',

    async init() {
      try {
        const response = await fetch('/api/words');
        if (!response.ok) {
          throw new Error(`Request failed with status ${response.status}`);
        }

        const data = await response.json();
        this.words = Array.isArray(data) ? data : [];
      } catch (err) {
        this.error = err.message || 'Unexpected error.';
      } finally {
        this.loading = false;
      }
    },

    get availableTypes() {
      const types = new Set(['all']);
      this.words.forEach((word) => {
        if (word.type) {
          types.add(word.type);
        }
      });
      return Array.from(types);
    },

    optionLabel(option) {
      if (!option || option === 'unknown') return 'Unknown';
      if (option === 'all') return 'All types';
      return option.charAt(0).toUpperCase() + option.slice(1);
    },

    get filteredWords() {
      const normalizedQuery = this.query.trim().toLowerCase();
      return this.words.filter((word) => {
        const matchesType = this.typeFilter === 'all' || word.type === this.typeFilter;
        if (!normalizedQuery) {
          return matchesType;
        }

        const inWord = word.word?.toLowerCase().includes(normalizedQuery);
        const inMeaning = word.meanings?.some((meaning) =>
          meaning.toLowerCase().includes(normalizedQuery),
        );
        const inExamples = word.examples?.some((example) =>
          example.toLowerCase().includes(normalizedQuery),
        );

        return matchesType && (inWord || inMeaning || inExamples);
      });
    },
  }));
});

