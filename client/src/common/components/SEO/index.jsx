import { Helmet } from "react-helmet-async";

const Seo = ({ title, description, type, name }) => {
  return (
    <Helmet>
      {/* HTML meta tag list */}
      <title>{title}</title>
      <meta name="description" content={description} />

      {/* Facebook meta tag list */}
      <meta property="og:url" content="https://linkloud.io" />
      <meta property="og:type" content={type} />
      <meta property="og:title" content={title} />
      <meta property="og:description" content={description} />

      {/* Twitter meta tag list */}
      <meta property="twitter:domain" content="linkloud.io"></meta>
      <meta name="twitter:creator" content={name} />
      <meta name="twitter:card" content={type} />
      <meta name="twitter:title" content={title} />
      <meta name="twitter:description" content={description} />
    </Helmet>
  );
};

export default Seo;
