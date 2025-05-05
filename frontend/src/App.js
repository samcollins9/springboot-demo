import React, { useState } from 'react';

function App() {
  const [formData, setFormData] = useState({
    prompt: 'Provide a random fun fact.',
    model: 'gpt-3.5-turbo',
    temperature: 0.7,
    max_tokens: 150,
    frequency_penalty: 0.0,
  });

  const [latestResponse, setLatestResponse] = useState('');
  const [allResponses, setAllResponses] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: name === 'temperature' || name === 'frequency_penalty' ? parseFloat(value)
              : name === 'max_tokens' ? parseInt(value)
              : value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);

    fetch('/api/greeting', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formData),
    })
      .then(res => res.json())
      .then(data => {
        setLatestResponse(data.latestResponse);
        setAllResponses(data.allResponsesToday);
        setLoading(false);
      })
      .catch(err => {
        console.error('Error:', err);
        setLoading(false);
      });
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Custom OpenAI Prompt</h1>
      <form onSubmit={handleSubmit} style={{ maxWidth: '600px' }}>
        <label>Prompt:
          <textarea
            name="prompt"
            value={formData.prompt}
            onChange={handleChange}
            rows={3}
            style={{ width: '100%' }}
          />
        </label>

        <label>Model:
          <select name="model" value={formData.model} onChange={handleChange}>
            <option value="gpt-3.5-turbo">gpt-3.5-turbo</option>
            <option value="gpt-4">gpt-4</option>
          </select>
        </label>

        <label>Temperature: {formData.temperature}
          <input
            type="range"
            name="temperature"
            min="0"
            max="2"
            step="0.1"
            value={formData.temperature}
            onChange={handleChange}
          />
        </label>

        <label>Max Tokens:
          <input
            type="number"
            name="max_tokens"
            min="1"
            max="1000"
            value={formData.max_tokens}
            onChange={handleChange}
          />
        </label>

        <label>Frequency Penalty: {formData.frequency_penalty}
          <input
            type="range"
            name="frequency_penalty"
            min="-2"
            max="2"
            step="0.1"
            value={formData.frequency_penalty}
            onChange={handleChange}
          />
        </label>

        <button type="submit" style={{ marginTop: '1rem' }}>
          Generate Response
        </button>
      </form>

      {loading && <p>Loading...</p>}

      {latestResponse && (
        <>
          <h2>Latest Response:</h2>
          <blockquote>{latestResponse}</blockquote>
        </>
      )}

      {allResponses.length > 0 && (
        <>
          <h2>Today's Responses:</h2>
          <ul>
            {allResponses.map((r, i) => (
              <li key={i}>{r.responseText}</li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
}

export default App;
