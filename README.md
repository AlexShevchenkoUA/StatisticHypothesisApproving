# StatisticHypothesisApproving
Java application for different statistic hypotheses approving

<h1>Overview</h1>
<p>This project contains different examples of implemeting statistic test using Java language</p>
<h2>Files descrition</h2>
<ul>

<li>Package <code>ci</code> contains solutions for confidence interval building. General api interface provided by
<code>interface ConfidenceInterval</code> and it have to main implemetation: <code>SimpleConfidenceInterval</code> in general cases, and
<code>NormalDistributionConfidenceInterval</code>, when we exactly know that target distribution is normal.</li>

<li> Package <code>criteria</code> contatins statistic criteria for different hypothsis approving
  <ul>
    <li><code>ChiSquareCriteria</code> - implemetation of chi square criteria for testing hypothesis about type of distribution</li>
    <li><code>EmptyBoxesCriteria</code> - implemetation of criteria for tesing hypothesis that distribution is uniform on interval [0, 1]</li>
    <li><code>SmivnovCriteria</code> - implemetation of criteria for testing that two samples are from the same probability distribution</li>
  </ul>
</li>

<li><code>Class DistributionSample</code> - provides general sampling mechanism from target distribution by using inverse cumulative probability function.</li>

<li><code>Class EmpiricalDistributionFunction</code> - simple implemetation of empirical function using sampel.</li>

<li>Test <code>Class ProbabilityEstimationTest</code> - shows different appoaches for estimating value.</li>

</ul>
