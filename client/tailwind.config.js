/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: {
          100: "#E3EDFE",
          200: "#C7DBFD",
          300: "#AAC4F9",
          400: "#92B0F3",
          500: "#6F92EC",
          600: "#516FCA",
          700: "#3751A9",
          800: "#233788",
          900: "#152471",
          high: "#7291ED",
          medium: "#5276CE",
          low: "#FBFCFF",
        },
        success: {
          high: "#67AA0C",
          medium: "#81C611",
          low: "#A8DC45",
        },
        warn: {
          high: "#DB402E",
          medium: "#FF623F",
          low: "#FF946F",
        },
      },
      maxWidth: {
        "2xs": "270px",
        sm: "368px",
        xl: "564px",
        "3xl": "808px",
        "7xl": "1200px",
      },
    },
  },
  plugins: [],
};
